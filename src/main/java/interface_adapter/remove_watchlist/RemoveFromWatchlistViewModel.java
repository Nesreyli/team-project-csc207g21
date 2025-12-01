package interface_adapter.remove_watchlist;

import interface_adapter.ViewModel;

/**
 * The View Model for the Remove from Watchlist Use Case.
 */

public class RemoveFromWatchlistViewModel extends ViewModel<RemoveFromWatchlistState> {
    public RemoveFromWatchlistViewModel() {
        super("removeFromWatchlist");
        setState(new RemoveFromWatchlistState());
    }
}
