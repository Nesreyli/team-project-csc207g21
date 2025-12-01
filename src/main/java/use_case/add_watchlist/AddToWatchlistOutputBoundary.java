package use_case.add_watchlist;

/**
 * Output boundary for the Add to Watchlist use case.
 */

public interface AddToWatchlistOutputBoundary {
    /**
     * Prepares the view for a successful addition to the watchlist.
     *
     * @param outputData the data containing the information needed to display
     *                   the success response to the user
     */
    void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData);

    /**
     * Prepares the view for a failed attempt to add a symbol to the watchlist.
     *
     * @param errorMessage a message describing why the operation failed
     */
    void prepareAddToWatchlistFailView(String errorMessage);
}
