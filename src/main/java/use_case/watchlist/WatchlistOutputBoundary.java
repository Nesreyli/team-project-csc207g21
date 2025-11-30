package use_case.watchlist;

public interface WatchlistOutputBoundary {
    void prepareWatchlistSuccessView(WatchlistOutputData outputData);
    void prepareWatchlistFailView(String errorMessage);
}
