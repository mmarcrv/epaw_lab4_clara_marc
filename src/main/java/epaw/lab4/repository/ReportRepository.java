package epaw.lab4.repository;

import epaw.lab4.model.ReportedItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportRepository extends BaseRepository {

    private static ReportRepository instance;

    private ReportRepository() {
        super();
    }

    public static synchronized ReportRepository getInstance() {
        if (instance == null) {
            instance = new ReportRepository();
        }
        return instance;
    }

    /** One user can only report a tweet once (UNIQUE constraint). Returns false if already reported. */
    public boolean saveReport(int tweetId, int reporterId, String reason) {
        String sql = "INSERT OR IGNORE INTO reports (tweet_id, reporter_id, reason) VALUES (?,?,?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, tweetId);
            stmt.setInt(2, reporterId);
            stmt.setString(3, reason);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<ReportedItem> getReportedPosts() {
        List<ReportedItem> items = new ArrayList<>();
        String query =
            "SELECT t.id, t.textBody, u.firstName, u.name as username, u.id as user_id, t.time, " +
            "MAX(r.reason) as reason, COUNT(r.id) as cnt " +
            "FROM reports r " +
            "JOIN tweets t ON r.tweet_id = t.id " +
            "JOIN users u ON t.user_id = u.id " +
            "WHERE t.is_parent = 1 " +
            "GROUP BY t.id " +
            "ORDER BY cnt DESC";
        try (PreparedStatement stmt = db.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ReportedItem item = new ReportedItem();
                item.setTweetId(rs.getInt("id"));
                item.setTextBody(rs.getString("textBody"));
                item.setAuthorName(rs.getString("firstName"));
                item.setAuthorHandle(rs.getString("username"));
                item.setAuthorId(rs.getInt("user_id"));
                item.setTime(rs.getString("time"));
                item.setReason(rs.getString("reason"));
                item.setReportCount(rs.getInt("cnt"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReportedItem> getReportedComments() {
        List<ReportedItem> items = new ArrayList<>();
        String query =
            "SELECT t.id, t.textBody, u.firstName, u.name as username, u.id as user_id, t.time, " +
            "MAX(r.reason) as reason, COUNT(r.id) as cnt, " +
            "(SELECT textBody FROM tweets WHERE id = t.parent_id) as parent_text " +
            "FROM reports r " +
            "JOIN tweets t ON r.tweet_id = t.id " +
            "JOIN users u ON t.user_id = u.id " +
            "WHERE t.is_parent = 0 " +
            "GROUP BY t.id " +
            "ORDER BY cnt DESC";
        try (PreparedStatement stmt = db.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ReportedItem item = new ReportedItem();
                item.setTweetId(rs.getInt("id"));
                item.setTextBody(rs.getString("textBody"));
                item.setAuthorName(rs.getString("firstName"));
                item.setAuthorHandle(rs.getString("username"));
                item.setAuthorId(rs.getInt("user_id"));
                item.setTime(rs.getString("time"));
                item.setReason(rs.getString("reason"));
                item.setReportCount(rs.getInt("cnt"));
                item.setParentTextBodySnippet(rs.getString("parent_text"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void ignoreReport(int tweetId) {
        String query = "DELETE FROM reports WHERE tweet_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTweet(int tweetId) {
        String query = "DELETE FROM tweets WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void banUser(int userId) {
        String query = "UPDATE users SET role = 'banned' WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String deleteTweets = "DELETE FROM tweets WHERE user_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(deleteTweets)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();

        String qReports = "SELECT COUNT(DISTINCT tweet_id) FROM reports";
        try (PreparedStatement stmt = db.prepareStatement(qReports);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("pendingReports", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        String qUsers = "SELECT COUNT(*) FROM users WHERE role != 'banned'";
        try (PreparedStatement stmt = db.prepareStatement(qUsers);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("activeUsers", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        String qPosts = "SELECT COUNT(*) FROM tweets WHERE is_parent = 1 AND date(time) = date('now')";
        try (PreparedStatement stmt = db.prepareStatement(qPosts);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("postsToday", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        String qComments = "SELECT COUNT(*) FROM tweets WHERE is_parent = 0 AND date(time) = date('now')";
        try (PreparedStatement stmt = db.prepareStatement(qComments);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("commentsToday", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        return stats;
    }
}
