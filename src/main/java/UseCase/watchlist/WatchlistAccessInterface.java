package UseCase.watchlist;

import Entity.WatchlistEntry;

import java.util.List;

public interface WatchlistAccessInterface {
    List<WatchlistEntry> getWatchlist(String username, String password);
    void addToWatchlist(String username, String password, String symbol);
    void removeFromWatchlist(String username, String password, String symbol);

}
