package interface_adapter.remove_watchlist;

import use_case.remove_watchlist.RemoveFromWatchlistInputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistInputData;

/**
 * The Controller for the Remove from Watchlist Use Case.
 */

public class RemoveFromWatchlistController {
    private final RemoveFromWatchlistInputBoundary inputBoundary;

    public RemoveFromWatchlistController(RemoveFromWatchlistInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void remove(String username, String password, String symbol) {
        RemoveFromWatchlistInputData data = new RemoveFromWatchlistInputData(username, password, symbol);
        inputBoundary.execute(data);
    }
}
