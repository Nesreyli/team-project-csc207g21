package use_case.remove_watchlist;

/**
 * The Output Boundary for the Remove From Watchlist Use Case.
 */

public interface RemoveFromWatchlistOutputBoundary {

    /**
     * Prepares the view for a successful removal from the watchlist.
     *
     * @param outputData the output data containing the removed symbol and updated watchlist
     */
    void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData);

    /**
     * Prepares the view when the removal operation fails.
     *
     * @param errorMessage a message describing the failure reason
     */
    void prepareRemoveFromWatchlistFailView(String errorMessage);
}
