package epaw.lab4.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRepository extends BaseRepository {

    private static LikeRepository instance;

    private LikeRepository() { super(); }

    public static synchronized LikeRepository getInstance() {
        if (instance == null) instance = new LikeRepository();
        return instance;
    }

    /* Retorna true si el like s'ha inserit, false si ja existia */
    public boolean save(int tweetId, int userId) {
        String query = "INSERT OR IGNORE INTO likes (tweet_id, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                incrementLikes(tweetId);
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private void incrementLikes(int tweetId) {
        String query = "UPDATE tweets SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int tweetId, int userId) {
        String query = "DELETE FROM likes WHERE tweet_id = ? AND user_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0) decrementLikes(tweetId);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void decrementLikes(int tweetId) {
        String query = "UPDATE tweets SET likes = MAX(0, likes - 1) WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public int countByTweet(int tweetId) {
        String query = "SELECT COUNT(*) FROM likes WHERE tweet_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public boolean hasLiked(int tweetId, int userId) {
        String query = "SELECT COUNT(*) FROM likes WHERE tweet_id = ? AND user_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, tweetId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
