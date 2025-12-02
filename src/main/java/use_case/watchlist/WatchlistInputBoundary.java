package use_case.watchlist;

/**
 * The Input Boundary for the Watchlist Use Case.
 */

public interface WatchlistInputBoundary {
    /**
     * Executes the Watchlist use case using the provided input data.
     * @param input the data containing the user's credentials and any other
     *              parameters needed to retrieve the watchlist
     */
    void execute(WatchlistInputData input);
    void executeAndNavigate(WatchlistInputData input);
}
