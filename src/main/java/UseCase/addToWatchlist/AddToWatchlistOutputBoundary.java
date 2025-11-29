package UseCase.addToWatchlist;

public interface AddToWatchlistOutputBoundary {
    void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData);
    void prepareAddToWatchlistFailView(String errorMessage);
}
