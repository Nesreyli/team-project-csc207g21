package use_case.watchlist;

/**
 * The Output Boundary for the Watchlist Use Case.
 */

public interface WatchlistOutputBoundary {
    /**
     * Prepares the view for a successful retrieval of the watchlist.
     * @param outputData the data containing the user's watchlist information
     */
    void prepareWatchlistSuccessView(WatchlistOutputData outputData);

    /**
     * Prepares the view when retrieving the watchlist fails.
     * @param errorMessage a message explaining why the operation failed
     */
    void prepareWatchlistFailView(String errorMessage);
}
