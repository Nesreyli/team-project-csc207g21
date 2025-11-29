package InterfaceAdapter.addToWatchlist;

import InterfaceAdapter.ViewModel;

public class AddToWatchlistViewModel extends ViewModel<AddToWatchlistState> {
    public AddToWatchlistViewModel() {
        super("addToWatchlist");
        setState(new AddToWatchlistState());
    }
}
