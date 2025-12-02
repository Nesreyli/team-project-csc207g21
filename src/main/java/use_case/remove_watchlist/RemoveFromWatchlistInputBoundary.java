package use_case.remove_watchlist;

/**
 * The Input Boundary for the Remove From Watchlist Use Case.
 */
public interface RemoveFromWatchlistInputBoundary {

    /**
     * Executes the removal of a symbol from the watchlist using the provided input data.
     *
     * @param inputData the input data containing username, password, and symbol
     */
    void execute(RemoveFromWatchlistInputData inputData);
}
