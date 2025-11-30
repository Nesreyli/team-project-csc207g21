package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import application.use_case.watchlist.WatchlistDatabaseInterface;
import application.use_case.watchlist.WatchlistEntry;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WatchlistDatabaseAccess implements WatchlistDatabaseInterface {
    private String url;

    public WatchlistDatabaseAccess() {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public WatchlistDatabaseAccess(String url) {
        this.url = url;
    }

    /**
     * New User.
     * @param userId user ID.
     */
    @Override
    public void newUser(int userId) {
    }

    /**
     * Gets Watchlist of user.
     * @param userId user ID
     * @return returns list of watchlist
     */
    @Override
    public List<WatchlistEntry> getWatchlistForUser(int userId) {
        final List<WatchlistEntry> list = new ArrayList<>();
        final String sql = "SELECT symbol FROM watchlist WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new WatchlistEntry(rs.getString("symbol")));
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public void addToWatchlist(int userId, String symbol) {
        final String sql = "INSERT INTO watchlist(user_id, symbol) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, symbol);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to add to watchlist", ex);
        }
    }

    @Override
    public void removeFromWatchlist(int userId, String symbol) {
        final String sql = "DELETE FROM watchlist WHERE user_id = ? AND symbol = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, symbol);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
