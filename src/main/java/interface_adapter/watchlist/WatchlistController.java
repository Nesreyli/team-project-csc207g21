package interface_adapter.watchlist;

import use_case.add_watchlist.AddToWatchlistInputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistInputBoundary;
import use_case.watchlist.WatchlistInputBoundary;
import use_case.add_watchlist.AddToWatchlistInputData;
import use_case.remove_watchlist.RemoveFromWatchlistInputData;
import use_case.watchlist.WatchlistInputData;

/**
 * The Controller for the Watchlist Use Case.
 */
public class WatchlistController {

    private final WatchlistInputBoundary fetchInteractor;
    private final AddToWatchlistInputBoundary addInteractor;
    private final RemoveFromWatchlistInputBoundary removeInteractor;

    public WatchlistController(
            WatchlistInputBoundary fetchInteractor,
            AddToWatchlistInputBoundary addInteractor,
            RemoveFromWatchlistInputBoundary removeInteractor
    ) {
        this.fetchInteractor = fetchInteractor;
        this.addInteractor = addInteractor;
        this.removeInteractor = removeInteractor;
    }

    public void fetchSilently(String username, String password) {
        WatchlistInputData inputData = new WatchlistInputData(username, password);
        fetchInteractor.execute(inputData);
    }

    public void openWatchlist(String username, String password) {
        WatchlistInputData inputData = new WatchlistInputData(username, password);
        fetchInteractor.executeAndNavigate(inputData);
    }

    public void add(String username, String password, String symbol) {
        AddToWatchlistInputData inputData = new AddToWatchlistInputData(username, password, symbol);
        addInteractor.execute(inputData);
    }

    public void remove(String username, String password, String symbol) {
        RemoveFromWatchlistInputData inputData = new RemoveFromWatchlistInputData(username, password, symbol);
        removeInteractor.execute(inputData);
    }
}
