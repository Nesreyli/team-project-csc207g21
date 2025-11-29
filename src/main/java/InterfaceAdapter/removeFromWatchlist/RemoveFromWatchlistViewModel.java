package InterfaceAdapter.removeFromWatchlist;

import InterfaceAdapter.ViewModel;

public class RemoveFromWatchlistViewModel extends ViewModel<RemoveFromWatchlistState> {
    public RemoveFromWatchlistViewModel() {
        super("removeFromWatchlist");
        setState(new RemoveFromWatchlistState());
    }
}
