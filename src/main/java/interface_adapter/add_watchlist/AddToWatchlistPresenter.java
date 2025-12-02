package interface_adapter.add_watchlist;

import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.add_watchlist.AddToWatchlistOutputBoundary;
import use_case.add_watchlist.AddToWatchlistOutputData;

public class AddToWatchlistPresenter implements AddToWatchlistOutputBoundary {

    private final AddToWatchlistViewModel addVM;
    private final WatchlistViewModel watchlistVM;

    public AddToWatchlistPresenter(AddToWatchlistViewModel addVM, WatchlistViewModel watchlistVM) {
        this.addVM = addVM;
        this.watchlistVM = watchlistVM;
    }

    /**
     * success view
     * @param outputData the data containing the information needed to display
     *                   the success response to the user
     */
    @Override
    public void prepareAddToWatchlistSuccessView(AddToWatchlistOutputData outputData) {
        addVM.getState().setLastAddedSymbol(outputData.getSymbol());
        addVM.getState().setLastMessage(outputData.getMessage());
        addVM.firePropertyChange();
        WatchlistState watchlistState = watchlistVM.getState();
        watchlistState.setSymbols(outputData.getUpdatedSymbols());
        watchlistVM.firePropertyChange();
    }

    /**
     * fail view
     * @param errorMessage a message describing why the operation failed
     */
    @Override
    public void prepareAddToWatchlistFailView(String errorMessage) {
        addVM.getState().setLastAddedSymbol(null);
        addVM.getState().setLastMessage(errorMessage);
        addVM.firePropertyChange();
    }
}
