package InterfaceAdapter.watchlist;

import UseCase.watchlist.WatchlistInputBoundary;
import UseCase.watchlist.WatchlistInputData;

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

