package epaw.lab4.repository;

import epaw.lab4.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FollowRepository extends BaseRepository {

    private static FollowRepository instance;

    private FollowRepository() { super(); }

    public static synchronized FollowRepository getInstance() {
        if (instance == null) instance = new FollowRepository();
        return instance;
    }

    /* Creates a pending follow request */
    public void save(int uFollowing, int uFollowed) {
        String query = "INSERT OR IGNORE INTO follows (u_following, u_followed, status) VALUES (?, ?, 'pending')";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uFollowing);
            stmt.setInt(2, uFollowed);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /* Accepts a pending follow request */
    public void accept(int uFollowing, int uFollowed) {
        String query = "UPDATE follows SET status = 'accepted' WHERE u_following = ? AND u_followed = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uFollowing);
            stmt.setInt(2, uFollowed);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int uFollowing, int uFollowed) {
        String query = "DELETE FROM follows WHERE u_following = ? AND u_followed = ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uFollowing);
            stmt.setInt(2, uFollowed);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /* Accepted follows of uid */
    public Optional<List<User>> findFollowing(int uid, int start, int end) {
        String query = "SELECT u.id, u.name, u.picture FROM users u " +
                       "JOIN follows f ON u.id = f.u_followed " +
                       "WHERE f.u_following = ? AND f.status = 'accepted' ORDER BY u.name LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, start);
            stmt.setInt(3, end);
            return Optional.of(mapUsers(stmt));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    /* Random users that uid has not followed or sent a request to */
    public Optional<List<User>> findNotFollowing(int uid, int start, int end) {
        String query = "SELECT id, name, picture FROM users " +
                       "WHERE id NOT IN (SELECT u_followed FROM follows WHERE u_following = ?) " +
                       "AND id <> ? ORDER BY RANDOM() LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, uid);
            stmt.setInt(3, start);
            stmt.setInt(4, end);
            return Optional.of(mapUsers(stmt));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    /* Incoming pending follow requests for uid */
    public Optional<List<User>> findPendingRequests(int uid, int start, int end) {
        String query = "SELECT u.id, u.name, u.picture FROM users u " +
                       "JOIN follows f ON u.id = f.u_following " +
                       "WHERE f.u_followed = ? AND f.status = 'pending' ORDER BY f.followed_at DESC LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, start);
            stmt.setInt(3, end);
            return Optional.of(mapUsers(stmt));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    /* Count accepted follows of uid */
    public int countFollowing(int uid) {
        String query = "SELECT COUNT(*) FROM follows WHERE u_following = ? AND status = 'accepted'";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    /* Count accepted followers of uid */
    public int countFollowers(int uid) {
        String query = "SELECT COUNT(*) FROM follows WHERE u_followed = ? AND status = 'accepted'";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private List<User> mapUsers(PreparedStatement stmt) throws SQLException {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setPicture(rs.getString("picture"));
                users.add(u);
            }
        }
        return users;
    }
}
