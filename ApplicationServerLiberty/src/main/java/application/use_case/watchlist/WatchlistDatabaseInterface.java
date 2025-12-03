package application.use_case.watchlist;

import java.util.List;

public interface WatchlistDatabaseInterface {
    /**
     * Creates a new, empty watchlist entry for the specified user.
     * @param userId the unique identifier of the user
     */
    void newUser(int userId);

    /**
     * Retrieves all watchlist entries for a given user.
     * @param userId the unique identifier of the user
     * @return a list of {@link WatchlistEntry} objects for the user,
     *         or an empty list if no entries exist
     */
    List<WatchlistEntry> getWatchlistForUser(int userId);

    /**
     * Adds a stock symbol to the user's watchlist.
     * Implementations should avoid creating duplicate entries for the same symbol.
     * @param userId the unique identifier of the user
     * @param symbol the stock symbol to add
     */
    void addToWatchlist(int userId, String symbol);

    /**
     * Removes a stock symbol from the user's watchlist.
     * If the symbol does not exist in the user's watchlist, implementations
     * may choose to ignore the request or handle it based on system needs.
     * @param userId the unique identifier of the user
     * @param symbol the stock symbol to remove
     */
    void removeFromWatchlist(int userId, String symbol);
}
