package interface_adapter.watchlist;

import use_case.watchlist.WatchlistInputBoundary;
import use_case.watchlist.WatchlistInputData;

public class WatchlistController {
    private final WatchlistInputBoundary inputBoundary;

    public WatchlistController(WatchlistInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String username, String password) {
        WatchlistInputData inputData = new WatchlistInputData(username, password);
        inputBoundary.execute(inputData);
    }
}

