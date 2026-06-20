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

    public List<ReportedItem> getReportedPosts() {
        List<ReportedItem> items = new ArrayList<>();
        String query = "SELECT t.id, t.textBody, u.firstName, u.name as username, u.id as user_id, t.time, r.reason, r.count " +
                       "FROM reports r " +
                       "JOIN tweets t ON r.tweet_id = t.id " +
                       "JOIN users u ON t.user_id = u.id " +
                       "WHERE t.is_parent = 1 " +
                       "ORDER BY r.count DESC";
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
                item.setReportCount(rs.getInt("count"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReportedItem> getReportedComments() {
        List<ReportedItem> items = new ArrayList<>();
        String query = "SELECT t.id, t.textBody, u.firstName, u.name as username, u.id as user_id, t.time, r.reason, r.count, " +
                       "(SELECT textBody FROM tweets WHERE id = t.parent_id) as parent_text " +
                       "FROM reports r " +
                       "JOIN tweets t ON r.tweet_id = t.id " +
                       "JOIN users u ON t.user_id = u.id " +
                       "WHERE t.is_parent = 0 " +
                       "ORDER BY r.count DESC";
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
                item.setReportCount(rs.getInt("count"));
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
        // Change user role to banned
        String query = "UPDATE users SET role = 'banned' WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Also delete all tweets by this user
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
        
        // 1. Pending reports sum
        String qReports = "SELECT COALESCE(SUM(count), 0) FROM reports";
        try (PreparedStatement stmt = db.prepareStatement(qReports);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("pendingReports", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        // 2. Active users (banned are role='banned')
        String qUsers = "SELECT COUNT(*) FROM users WHERE role != 'banned'";
        try (PreparedStatement stmt = db.prepareStatement(qUsers);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("activeUsers", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        // 3. Posts today
        String qPosts = "SELECT COUNT(*) FROM tweets WHERE is_parent = 1 AND date(time) = date('now')";
        try (PreparedStatement stmt = db.prepareStatement(qPosts);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("postsToday", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        // 4. Comments today
        String qComments = "SELECT COUNT(*) FROM tweets WHERE is_parent = 0 AND date(time) = date('now')";
        try (PreparedStatement stmt = db.prepareStatement(qComments);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) stats.put("commentsToday", rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }

        return stats;
    }
}
