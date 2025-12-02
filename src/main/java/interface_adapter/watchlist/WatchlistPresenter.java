package interface_adapter.watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.watchlist.WatchlistOutputBoundary;
import use_case.watchlist.WatchlistOutputData;

/**
 * The Presenter for the Watchlist Use Case.
 */

public class WatchlistPresenter implements WatchlistOutputBoundary {
    private final WatchlistViewModel watchlistViewModel;
    private final ViewManagerModel viewManager;
    private final LoggedInViewModel loggedInViewModel;

    public WatchlistPresenter(ViewManagerModel viewManager, WatchlistViewModel watchlistViewModel, LoggedInViewModel loggedInViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.viewManager = viewManager;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareWatchlistSuccessView(WatchlistOutputData data) {
        WatchlistState state = watchlistViewModel.getState();
        state.setUsername(data.getUsername());
        state.setPassword(data.getPassword());
        state.setSymbols(data.getSymbols());
        state.setPrices(data.getPrices());
        state.setPerformance(data.getPerformance());
        state.setMessage("200");

        watchlistViewModel.firePropertyChange();
    }

    @Override
    public void prepareWatchlistFailView(String error) {
        WatchlistState state = watchlistViewModel.getState();
        state.setMessage(error);
        watchlistViewModel.firePropertyChange();
    }

    @Override
    public void switchToWatchlistView() {
        viewManager.setState(watchlistViewModel.getViewName());
        viewManager.firePropertyChange();
    }
}
