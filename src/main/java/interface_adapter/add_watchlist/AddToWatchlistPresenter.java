package interface_adapter.add_watchlist;

import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.add_watchlist.AddToWatchlistOutputBoundary;
import use_case.add_watchlist.AddToWatchlistOutputData;

/**
 * The Presenter for the Add to Watchlist Use Case.
 */

public class AddToWatchlistPresenter implements AddToWatchlistOutputBoundary {

    private final AddToWatchlistViewModel addToWatchlistViewModel;
    private final WatchlistViewModel watchlistViewModel;

    public AddToWatchlistPresenter(AddToWatchlistViewModel addToWatchlistViewModel, WatchlistViewModel watchlistViewModel) {
        this.addToWatchlistViewModel = addToWatchlistViewModel;
        this.watchlistViewModel = watchlistViewModel;
    }

    @Override
    public void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData) {
        addToWatchlistViewModel.getState().setLastAddedSymbol(outputData.getSymbol());
        addToWatchlistViewModel.getState().setLastMessage(outputData.getMessage());
        addToWatchlistViewModel.firePropertyChange();

        final WatchlistState watchlistState = watchlistViewModel.getState();
        watchlistState.setSymbols(outputData.getUpdatedSymbols());
        watchlistViewModel.firePropertyChange();
    }

    @Override
    public void prepareAddToWatchlistFailView(String errorMessage) {
        addToWatchlistViewModel.getState().setLastAddedSymbol(null);
        addToWatchlistViewModel.getState().setLastMessage(errorMessage);
        addToWatchlistViewModel.firePropertyChange();
    }
}