package application.database;

import application.use_case.watchlist.WatchlistDatabaseInterface;
import application.use_case.watchlist.WatchlistEntry;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WatchlistDatabaseAccess implements WatchlistDatabaseInterface {
    private String url;

    public WatchlistDatabaseAccess() {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public WatchlistDatabaseAccess(String url) {
        this.url = url;
    }

    @Override
    public void newUser(int userId) {
    }

    @Override
    public List<WatchlistEntry> getWatchlistForUser(int userId) {
        List<WatchlistEntry> list = new ArrayList<>();
        String sql = "SELECT symbol FROM watchlist WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new WatchlistEntry(rs.getString("symbol")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addToWatchlist(int userId, String symbol) {
        String sql = "INSERT INTO watchlist(user_id, symbol) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, symbol);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add to watchlist", e);
        }
    }

    @Override
    public void removeFromWatchlist(int userId, String symbol) {
        String sql = "DELETE FROM watchlist WHERE user_id = ? AND symbol = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, symbol);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
