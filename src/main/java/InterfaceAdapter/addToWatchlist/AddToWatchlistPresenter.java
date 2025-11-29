package InterfaceAdapter.addToWatchlist;

import InterfaceAdapter.watchlist.WatchlistState;
import InterfaceAdapter.watchlist.WatchlistViewModel;
import UseCase.addToWatchlist.AddToWatchlistOutputBoundary;
import UseCase.addToWatchlist.AddToWatchlistOutputData;

public class AddToWatchlistPresenter implements AddToWatchlistOutputBoundary {

    private final AddToWatchlistViewModel addVM;
    private final WatchlistViewModel watchlistVM;

    public AddToWatchlistPresenter(AddToWatchlistViewModel addVM, WatchlistViewModel watchlistVM) {
        this.addVM = addVM;
        this.watchlistVM = watchlistVM;
    }

    @Override
    public void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData) {
        addVM.getState().setLastAddedSymbol(outputData.getSymbol());
        addVM.getState().setLastMessage(outputData.getMessage());
        addVM.firePropertyChange();
        WatchlistState watchlistState = watchlistVM.getState();
        watchlistState.setSymbols(outputData.getUpdatedSymbols());
        watchlistVM.firePropertyChange();
    }

    @Override
    public void prepareAddToWatchlistFailView(String errorMessage) {
        addVM.getState().setLastAddedSymbol(null);
        addVM.getState().setLastMessage(errorMessage);
        addVM.firePropertyChange();
    }
}
