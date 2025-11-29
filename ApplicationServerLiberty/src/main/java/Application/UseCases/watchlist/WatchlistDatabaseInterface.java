package Application.UseCases.watchlist;

import java.util.List;

public interface WatchlistDatabaseInterface {
    void newUser(int userId);
    List<WatchlistEntry> getWatchlistForUser(int userId);
    void addToWatchlist(int userId, String symbol);
    void removeFromWatchlist(int userId, String symbol);
}
