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
        WatchlistState newState = watchlistViewModel.getState();
        newState.setUsername(data.getUsername());
        newState.setPassword(data.getPassword());
        newState.setSymbols(data.getSymbols());
        newState.setPrices(data.getPrices());
        newState.setPerformance(data.getPerformance());

        watchlistViewModel.setState(newState);
        watchlistViewModel.firePropertyChange();
    }

    @Override
    public void prepareWatchlistFailView(String error) {
        final LoggedInState loginState = loggedInViewModel.getState();
        loginState.setLoggedInError(error);
        loggedInViewModel.firePropertyChange();
    }

    @Override
    public void switchToWatchlistView() {
        viewManager.setState(watchlistViewModel.getViewName());
        viewManager.firePropertyChange();
    }
}
