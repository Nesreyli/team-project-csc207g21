package interface_adapter.add_watchlist;

import interface_adapter.ViewModel;

/**
 * The View Model for the Add to Watchlist Use Case.
 */

public class AddToWatchlistViewModel extends ViewModel<AddToWatchlistState> {
    public AddToWatchlistViewModel() {
        super("addToWatchlist");
        setState(new AddToWatchlistState());
    }
}
