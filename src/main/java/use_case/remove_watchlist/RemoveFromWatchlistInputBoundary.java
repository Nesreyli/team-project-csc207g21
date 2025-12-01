package use_case.remove_watchlist;

/**
 * The Input Boundary for the Remove From Watchlist Use Case.
 */

public interface RemoveFromWatchlistInputBoundary {
    /**
     * Executes the removal of a symbol from a watchlist using the provided input data.
     * @param inputData the data containing information about the symbol and user
     */
    void execute(RemoveFromWatchlistInputData inputData);
}
