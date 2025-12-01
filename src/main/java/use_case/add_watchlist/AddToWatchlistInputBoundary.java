package use_case.add_watchlist;

/**
 * The output boundary for the Watchlist Use Case.
 */

public interface AddToWatchlistInputBoundary {
    /**
     * Executes the Add to Watchlist use case using the provided input data.
     *
     * @param inputData the data required to attempt adding a symbol to the user's watchlist
     */
    void execute(AddToWatchlistInputData inputData);
}
