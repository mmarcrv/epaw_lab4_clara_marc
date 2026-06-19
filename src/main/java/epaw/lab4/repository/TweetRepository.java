package epaw.lab4.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epaw.lab4.model.Tweet;

public class TweetRepository extends BaseRepository {

    private static TweetRepository instance;

    private TweetRepository() { super(); }

    public static synchronized TweetRepository getInstance() {
        if (instance == null) instance = new TweetRepository();
        return instance;
    }

    public void save(Tweet tweet) {
        String query = "INSERT INTO tweets (user_id, title, picture, textBody, is_parent, parent_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweet.getUserId());
            stmt.setString(2, tweet.getTitle());
            stmt.setString(3, tweet.getPicture());
            stmt.setString(4, tweet.getTextBody());
            stmt.setBoolean(5, tweet.getIsParent());
            if (tweet.getParentId() != null)
                stmt.setInt(6, tweet.getParentId());
            else
                stmt.setNull(6, Types.INTEGER);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(Integer id, Integer userId) {
        String query = "DELETE FROM tweets WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /* Timeline: tweets from user + people they follow, newest first */
    public Optional<List<Tweet>> findTimeline(int uid, int start, int end) {
        String query = "SELECT t.id, t.user_id, t.title, t.picture, t.textBody, t.time, " +
                       "t.likes, t.comments, t.is_parent, t.parent_id, u.name AS uname " +
                       "FROM tweets t JOIN users u ON t.user_id = u.id " +
                       "WHERE t.is_parent = 1 " +
                       "AND (t.user_id = ? OR t.user_id IN (SELECT u_followed FROM follows WHERE u_following = ?)) " +
                       "ORDER BY t.time DESC LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, uid);
            stmt.setInt(3, start);
            stmt.setInt(4, end);
            return Optional.of(mapRows(stmt));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    /* Only tweets by a specific user */
    public Optional<List<Tweet>> findByUser(int uid, int start, int end) {
        String query = "SELECT t.id, t.user_id, t.title, t.picture, t.textBody, t.time, " +
                       "t.likes, t.comments, t.is_parent, t.parent_id, u.name AS uname " +
                       "FROM tweets t JOIN users u ON t.user_id = u.id " +
                       "WHERE t.user_id = ? AND t.is_parent = 1 " +
                       "ORDER BY t.time DESC LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, start);
            stmt.setInt(3, end);
            return Optional.of(mapRows(stmt));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    private List<Tweet> mapRows(PreparedStatement stmt) throws SQLException {
        List<Tweet> tweets = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tweet t = new Tweet();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setUname(rs.getString("uname"));
                t.setTitle(rs.getString("title"));
                t.setPicture(rs.getString("picture"));
                t.setTextBody(rs.getString("textBody"));
                t.setTime(rs.getTimestamp("time"));
                t.setLikes(rs.getInt("likes"));
                t.setComments(rs.getInt("comments"));
                t.setIsParent(rs.getBoolean("is_parent"));
                int pid = rs.getInt("parent_id");
                if (!rs.wasNull()) t.setParentId(pid);
                tweets.add(t);
            }
        }
        return tweets;
    }
}
