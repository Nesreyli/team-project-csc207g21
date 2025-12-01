package interface_adapter.add_watchlist;

import use_case.add_watchlist.AddToWatchlistInputBoundary;
import use_case.add_watchlist.AddToWatchlistInputData;

/**
 * The Controller for the Add to Watchlist Use Case.
 */

public class AddToWatchlistController {
    private final AddToWatchlistInputBoundary inputBoundary;

    public AddToWatchlistController(AddToWatchlistInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void add(String username, String password, String symbol) {
        AddToWatchlistInputData data = new AddToWatchlistInputData(username, password, symbol);
        inputBoundary.execute(data);
    }
}
