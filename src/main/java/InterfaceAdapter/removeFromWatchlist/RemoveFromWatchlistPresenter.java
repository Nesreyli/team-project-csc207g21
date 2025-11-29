package InterfaceAdapter.removeFromWatchlist;

import InterfaceAdapter.watchlist.WatchlistState;
import InterfaceAdapter.watchlist.WatchlistViewModel;
import UseCase.removeFromWatchlist.RemoveFromWatchlistOutputBoundary;
import UseCase.removeFromWatchlist.RemoveFromWatchlistOutputData;

public class RemoveFromWatchlistPresenter implements RemoveFromWatchlistOutputBoundary {

    private final RemoveFromWatchlistViewModel removeVM;
    private final WatchlistViewModel watchlistVM;

    public RemoveFromWatchlistPresenter(RemoveFromWatchlistViewModel removeVM, WatchlistViewModel watchlistVM) {
        this.removeVM = removeVM;
        this.watchlistVM = watchlistVM;
    }

    @Override
    public void prepareRemoveFromWatchlistSuccessView(RemoveFromWatchlistOutputData outputData) {
        removeVM.getState().setLastRemovedSymbol(outputData.getSymbol());
        removeVM.getState().setLastMessage(outputData.getMessage());
        removeVM.firePropertyChange();

        WatchlistState watchlistState = watchlistVM.getState();
        watchlistState.setSymbols(outputData.getUpdatedSymbols());
        watchlistVM.firePropertyChange();
    }

    @Override
    public void prepareRemoveFromWatchlistFailView(String errorMessage) {
        removeVM.getState().setLastRemovedSymbol(null);
        removeVM.getState().setLastMessage(errorMessage);
        removeVM.firePropertyChange();
    }
}
