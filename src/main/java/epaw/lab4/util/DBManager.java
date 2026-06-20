package epaw.lab4.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.stream.Collectors;

public class DBManager {

	private static DBManager instance;
	private Connection connection = null;
	private static final String DB_FILE = "lab4.db";

	private DBManager() {
		try {
			// SQLite connection
			Class.forName("org.sqlite.JDBC");
			boolean dbExists = Files.exists(Paths.get(DB_FILE));
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);

			// Enable foreign keys in SQLite and create reports table
			try (Statement stmt = connection.createStatement()) {
				stmt.execute("PRAGMA foreign_keys = ON;");
				stmt.execute("CREATE TABLE IF NOT EXISTS reports ("
						+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ "tweet_id INTEGER NOT NULL UNIQUE, "
						+ "reason VARCHAR(255) NOT NULL, "
						+ "count INTEGER DEFAULT 1, "
						+ "FOREIGN KEY (tweet_id) REFERENCES tweets(id) ON DELETE CASCADE"
						+ ");");
			}

			if (!dbExists) {
				initDatabase();
			}

			seedReportsIfEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private void initDatabase() throws Exception {
		String schemaPath = "DB.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(schemaPath))) {
			String schema = reader.lines().collect(Collectors.joining("\n"));
			String[] statements = schema.split(";");
			try (Statement stmt = connection.createStatement()) {
				for (String sql : statements) {
					if (!sql.trim().isEmpty()) {
						stmt.execute(sql);
					}
				}
			}
		}
	}

	private void seedReportsIfEmpty() {
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM reports");
			if (rs.next() && rs.getInt(1) == 0) {
				// Ensure users exist
				int spamUserId = getOrCreateUser("spam_user", "Usuario Spam", "spam@example.com");
				int trollUserId = getOrCreateUser("troll_bcn", "Usuario Troll", "troll@example.com");
				int fakeUserId = getOrCreateUser("fake_account", "Usuario Sospitós", "fake@example.com");
				int molestUserId = getOrCreateUser("molest_user", "Usuario Molest", "molest@example.com");
				int parentUserId = getOrCreateUser("owner_user", "Propietari", "owner@example.com");

				// Insert tweets (posts)
				int postSpamId = insertTweet(spamUserId, "Spam", "COMPRA PISOS BARATOS!!! CLICA AQUÍ!!! www.estafa.com", null, true);
				int postTrollId = insertTweet(trollUserId, "Altres", "Contingut inapropiat i ofensiu...", null, true);
				int postFakeId = insertTweet(fakeUserId, "Lloguer", "Habitació súper barata només 50€!", null, true);
				
				int parentPostId = insertTweet(parentUserId, "Compartir", "Busco company de pis...", null, true);
				int commentMolestId = insertTweet(molestUserId, "Comentari", "Comentari ofensiu i inapropiat", parentPostId, false);

				// Insert reports
				insertReport(postSpamId, "Spam", 12);
				insertReport(postTrollId, "Contingut ofensiu", 8);
				insertReport(postFakeId, "Possible estafa", 5);
				insertReport(commentMolestId, "Contingut inapropiat", 6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getOrCreateUser(String username, String firstName, String email) throws SQLException {
		String query = "SELECT id FROM users WHERE name = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) return rs.getInt(1);
			}
		}
		String insert = "INSERT INTO users (name, password, firstName, lastName, email, dateOfBirth, comarca, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, username);
			stmt.setString(2, "Password123!");
			stmt.setString(3, firstName);
			stmt.setString(4, "Cognom");
			stmt.setString(5, email);
			stmt.setString(6, "1995-05-15");
			stmt.setString(7, "Barcelona");
			stmt.setString(8, "user");
			stmt.executeUpdate();
			try (ResultSet keys = stmt.getGeneratedKeys()) {
				if (keys.next()) return keys.getInt(1);
			}
		}
		return 0;
	}

	private int insertTweet(int userId, String category, String textBody, Integer parentId, boolean isParent) throws SQLException {
		String insert = "INSERT INTO tweets (user_id, parent_id, title, textBody, category, location, is_parent) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, userId);
			if (parentId != null) {
				stmt.setInt(2, parentId);
			} else {
				stmt.setNull(2, java.sql.Types.INTEGER);
			}
			stmt.setString(3, category);
			stmt.setString(4, textBody);
			stmt.setString(5, category);
			stmt.setString(6, "Barcelona");
			stmt.setBoolean(7, isParent);
			stmt.executeUpdate();
			try (ResultSet keys = stmt.getGeneratedKeys()) {
				if (keys.next()) return keys.getInt(1);
			}
		}
		return 0;
	}

	private void insertReport(int tweetId, String reason, int count) throws SQLException {
		String insert = "INSERT INTO reports (tweet_id, reason, count) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(insert)) {
			stmt.setInt(1, tweetId);
			stmt.setString(2, reason);
			stmt.setInt(3, count);
			stmt.executeUpdate();
		}
	}

	public PreparedStatement prepareStatement(String query) throws SQLException {
		return connection.prepareStatement(query);
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}