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
            if (tweet.getParentId() != null) {
                Integer rootId = findRootId(tweet.getParentId());
                incrementComments(rootId != null ? rootId : tweet.getParentId());
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(Integer id, Integer userId) {
        Integer parentId = getParentId(id);
        int totalToRemove = 1 + (parentId != null ? countDescendants(id) : 0);
        String query = "DELETE FROM tweets WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0 && parentId != null) {
                Integer rootId = findRootId(parentId);
                decrementComments(rootId != null ? rootId : parentId, totalToRemove);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /* All descendants (recursive) of a root tweet, ordered by time */
    public Optional<List<Tweet>> findDescendants(int rootId) {
        List<Tweet> result = new ArrayList<>();
        collectDescendants(rootId, result);
        result.sort((a, b) -> a.getTime().compareTo(b.getTime()));
        return Optional.of(result);
    }

    private void collectDescendants(int parentId, List<Tweet> result) {
        String query = "SELECT t.id, t.user_id, t.parent_id, t.title, t.picture, t.textBody, t.time, " +
                       "t.likes, t.comments, t.is_parent, u.name AS uname " +
                       "FROM tweets t JOIN users u ON t.user_id = u.id " +
                       "WHERE t.parent_id = ? ORDER BY t.time ASC";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, parentId);
            List<Tweet> children = mapRows(stmt);
            for (Tweet child : children) {
                result.add(child);
                collectDescendants(child.getId(), result);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Integer getParentId(int tweetId) {
        String query = "SELECT parent_id FROM tweets WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int pid = rs.getInt("parent_id");
                    return rs.wasNull() ? null : pid;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /* Walk up the tree to find the root tweet id */
    private Integer findRootId(int tweetId) {
        Integer parentId = getParentId(tweetId);
        if (parentId == null) return tweetId;
        return findRootId(parentId);
    }

    /* Count all descendants of a tweet (not including itself) */
    private int countDescendants(int tweetId) {
        int count = 0;
        String query = "SELECT id FROM tweets WHERE parent_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    count++;
                    count += countDescendants(rs.getInt("id"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    private void incrementComments(int tweetId) {
        String query = "UPDATE tweets SET comments = comments + 1 WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void decrementComments(int tweetId, int count) {
        String query = "UPDATE tweets SET comments = MAX(0, comments - ?) WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, count);
            stmt.setInt(2, tweetId);
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
