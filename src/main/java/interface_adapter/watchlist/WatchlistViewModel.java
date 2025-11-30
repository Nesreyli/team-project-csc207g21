package interface_adapter.watchlist;

import interface_adapter.ViewModel;

public class WatchlistViewModel extends ViewModel<WatchlistState> {
    public WatchlistViewModel() {
        super("watchlist");
        setState(new WatchlistState());
    }
}
