package use_case.add_watchlist;

/**
 * The Input Boundary for the Add to Watchlist Use Case.
 */
public interface AddToWatchlistInputBoundary {

    /**
     * Executes the Add to Watchlist use case using the provided input data.
     *
     * @param inputData the data containing username, password, and symbol
     */
    void execute(AddToWatchlistInputData inputData);
}
