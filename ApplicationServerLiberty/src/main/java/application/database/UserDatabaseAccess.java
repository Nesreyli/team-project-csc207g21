package application.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import application.use_case.User.UserDatabaseInterface;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDatabaseAccess implements UserDatabaseInterface {
    private final String url;

    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Adds new user to database.
     * @param username username
     * @param password password
     * @return true if works
     */
    public boolean addUser(String username, String password) {
        final String sql = "INSERT INTO user_record(username,password) VALUES(?,?)";
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            if (conn != null) {
                System.out.println(conn.getMetaData());
            }
        }
        catch (SQLException ex) {
            return false;
        }
        return true;
    }

    /**
     * Get user id of existing user.
     * @param username username
     * @param password password
     * @return int id
     * @throws RuntimeException when user doesn't exist
     */
    public Integer getUserID(String username, String password) {
        final String sql = "SELECT user_id,username FROM user_record WHERE username = ? AND password = ?";
        try (var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            final var rs = stmt.executeQuery();
            if (conn != null) {
                System.out.println(conn.getMetaData());
            }
            if (rs.next()) {
                return rs.getInt("user_id");
            }
            else {
                return null;
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        // return -1 or null or throw exception
    }

    /**
     * Get ids of all users.
     * @return Hashmap of id to username
     * @throws RuntimeException when sql error
     */
    public Map<Integer, String> getUserIds() {
        final Map<Integer, String> userIds = new HashMap<>();
        final String sql = "SELECT user_id,username FROM user_record";
        try (var conn = DriverManager.getConnection(url);
            var stmt = conn.createStatement()) {
            final var rs = stmt.executeQuery(sql);
            if (conn != null) {
                System.out.println(conn.getMetaData());
            }
            while (rs.next()) {
                userIds.put(rs.getInt(1), rs.getString(2));
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return userIds;
    }
}
