package use_case.remove_watchlist;

public interface RemoveFromWatchlistOutputBoundary {
    void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData);
    void prepareRemoveFromWatchlistFailView(String errorMessage);
}
