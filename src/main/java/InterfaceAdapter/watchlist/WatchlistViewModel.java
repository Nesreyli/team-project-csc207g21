package InterfaceAdapter.watchlist;

import InterfaceAdapter.ViewModel;

public class WatchlistViewModel extends ViewModel<WatchlistState> {
    public WatchlistViewModel() {
        super("watchlist");
        setState(new WatchlistState());
    }
}
