package use_case.add_watchlist;

public interface AddToWatchlistOutputBoundary {
    void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData);
    void prepareAddToWatchlistFailView(String errorMessage);
}
