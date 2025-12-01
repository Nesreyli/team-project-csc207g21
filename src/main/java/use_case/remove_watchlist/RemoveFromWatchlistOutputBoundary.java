package use_case.remove_watchlist;

public interface RemoveFromWatchlistOutputBoundary {
    /**
     * Prepares the view for a successful removal from the watchlist.
     * @param outputData the data containing information about the removed symbol
     */
    void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData);

    /**
     * Prepares the view when the removal from the watchlist fails.
     * @param errorMessage a message explaining why the operation failed
     */
    void prepareRemoveFromWatchlistFailView(String errorMessage);
}
