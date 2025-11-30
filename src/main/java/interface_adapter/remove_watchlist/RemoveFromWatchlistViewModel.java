package interface_adapter.remove_watchlist;

import interface_adapter.ViewModel;

public class RemoveFromWatchlistViewModel extends ViewModel<RemoveFromWatchlistState> {
    public RemoveFromWatchlistViewModel() {
        super("removeFromWatchlist");
        setState(new RemoveFromWatchlistState());
    }
}
