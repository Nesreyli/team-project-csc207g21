package interface_adapter.remove_watchlist;

import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.remove_watchlist.RemoveFromWatchlistOutputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistOutputData;

/**
 * The Presenter for the Remove from Watchlist Use Case.
 */

public class RemoveFromWatchlistPresenter implements RemoveFromWatchlistOutputBoundary {

    private final RemoveFromWatchlistViewModel removeFromWatchlistViewModel;
    private final WatchlistViewModel watchlistViewModel;

    public RemoveFromWatchlistPresenter(RemoveFromWatchlistViewModel removeFromWatchlistViewModel,
                                        WatchlistViewModel watchlistViewModel) {
        this.removeFromWatchlistViewModel = removeFromWatchlistViewModel;
        this.watchlistViewModel = watchlistViewModel;
    }

    @Override
    public void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData) {
        removeFromWatchlistViewModel.getState().setLastRemovedSymbol(outputData.getSymbol());
        removeFromWatchlistViewModel.getState().setLastMessage(outputData.getMessage());
        removeFromWatchlistViewModel.firePropertyChange();

        final WatchlistState state = watchlistViewModel.getState();
        state.setSymbols(outputData.getUpdatedSymbols());
        watchlistViewModel.firePropertyChange();
    }

    @Override
    public void prepareRemoveFromWatchlistFailView(String errorMessage) {
        removeFromWatchlistViewModel.getState().setLastRemovedSymbol(null);
        removeFromWatchlistViewModel.getState().setLastMessage(errorMessage);
        removeFromWatchlistViewModel.firePropertyChange();
    }
}