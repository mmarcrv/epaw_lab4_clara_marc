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

    public void save(int uFollowing, int uFollowed) {
        String query = "INSERT OR IGNORE INTO follows (u_following, u_followed) VALUES (?, ?)";
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

    /* People that uid follows */
    public Optional<List<User>> findFollowing(int uid, int start, int end) {
        String query = "SELECT users.id, users.name, users.picture FROM users " +
                       "JOIN follows ON users.id = follows.u_followed " +
                       "WHERE follows.u_following = ? ORDER BY users.name LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, start);
            stmt.setInt(3, end);
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setPicture(rs.getString("picture"));
                    users.add(user);
                }
                return Optional.of(users);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    /* People that uid does NOT follow (suggestions) */
    public Optional<List<User>> findNotFollowing(int uid, int start, int end) {
        String query = "SELECT id, name, picture FROM users " +
                       "WHERE id NOT IN (SELECT u_followed FROM follows WHERE u_following = ?) " +
                       "AND id <> ? ORDER BY name LIMIT ?, ?";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, uid);
            stmt.setInt(2, uid);
            stmt.setInt(3, start);
            stmt.setInt(4, end);
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setPicture(rs.getString("picture"));
                    users.add(user);
                }
                return Optional.of(users);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }
}
