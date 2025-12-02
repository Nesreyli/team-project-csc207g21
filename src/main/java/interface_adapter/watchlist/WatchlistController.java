package interface_adapter.watchlist;

import use_case.add_watchlist.AddToWatchlistInputBoundary;
import use_case.add_watchlist.AddToWatchlistInputData;
import use_case.remove_watchlist.RemoveFromWatchlistInputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistInputData;
import use_case.watchlist.WatchlistInputBoundary;
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

    /**
     * Retrieves the current state data.
     * @param username the username of the current state data
     * @param password the password of the current state data
     */
    public void fetchSilently(String username, String password) {
        final WatchlistInputData inputData = new WatchlistInputData(username, password);
        fetchInteractor.execute(inputData);
    }

    /**
     * Retrieves the current state data and navigates to WatchlistView.
     * @param username the username of the current state data
     * @param password the password of the current state data
     */
    public void openWatchlist(String username, String password) {
        final WatchlistInputData inputData = new WatchlistInputData(username, password);
        fetchInteractor.executeAndNavigate(inputData);
    }

    /**
     * Adds the stock symbol to the user's watchlist.
     * @param username the username of the current state data
     * @param password the password of the current state data
     * @param symbol the stock symbol to add
     */
    public void add(String username, String password, String symbol) {
        final AddToWatchlistInputData inputData = new AddToWatchlistInputData(username, password, symbol);
        addInteractor.execute(inputData);
    }

    /**
     * Removes the stock symbol from the user's watchlist.
     * @param username the username of the current state data
     * @param password the password of the current state data
     * @param symbol the stock symbol to remove
     */
    public void remove(String username, String password, String symbol) {
        final RemoveFromWatchlistInputData inputData = new RemoveFromWatchlistInputData(username, password, symbol);
        removeInteractor.execute(inputData);
    }
}
