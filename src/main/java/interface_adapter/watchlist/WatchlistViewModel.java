package interface_adapter.watchlist;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Watchlist Use Case.
 */

public class WatchlistViewModel extends ViewModel<WatchlistState> {

    public WatchlistViewModel() {
        super("watchlist");
        setState(new WatchlistState());
    }
}
