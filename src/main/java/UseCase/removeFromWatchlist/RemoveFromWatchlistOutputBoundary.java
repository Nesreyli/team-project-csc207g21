package UseCase.removeFromWatchlist;

public interface RemoveFromWatchlistOutputBoundary {
    void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData);
    void prepareRemoveFromWatchlistFailView(String errorMessage);
}
