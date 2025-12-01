package use_case.watchlist;

import java.util.List;

import entity.WatchlistEntry;

/**
 * The Access Interface for the Watchlist Use Case.
 */

public interface WatchlistAccessInterface {
    /**
     * Retrieves the watchlist for a given user.
     * @param username the username of the user
     * @param password the password of the user
     * @return a list of WatchlistEntry objects representing the user's watchlist
     */
    List<WatchlistEntry> getWatchlist(String username, String password);

    /**
     * Adds a stock symbol to the user's watchlist.
     * @param username the username of the user
     * @param password the password of the user
     * @param symbol the stock symbol to add
     */
    void addToWatchlist(String username, String password, String symbol);

    /**
     * Removes a stock symbol from the user's watchlist.
     * @param username the username of the user
     * @param password the password of the user
     * @param symbol   the stock symbol to remove
     */
    void removeFromWatchlist(String username, String password, String symbol);

}
