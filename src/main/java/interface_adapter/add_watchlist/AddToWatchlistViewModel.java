package interface_adapter.add_watchlist;

import interface_adapter.ViewModel;

public class AddToWatchlistViewModel extends ViewModel<AddToWatchlistState> {
    public AddToWatchlistViewModel() {
        super("addToWatchlist");
        setState(new AddToWatchlistState());
    }
}
