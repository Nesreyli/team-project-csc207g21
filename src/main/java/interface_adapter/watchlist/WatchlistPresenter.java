package interface_adapter.watchlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.watchlist.WatchlistOutputBoundary;
import use_case.watchlist.WatchlistOutputData;

public class WatchlistPresenter implements WatchlistOutputBoundary {
    private final WatchlistViewModel viewModel;
    private final ViewManagerModel viewManager;
    private final LoggedInViewModel loggedInVM;

    public WatchlistPresenter(ViewManagerModel viewManager, WatchlistViewModel viewModel, LoggedInViewModel loggedInVM) {
        this.viewModel = viewModel;
        this.viewManager = viewManager;
        this.loggedInVM = loggedInVM;
    }

    @Override
    public void prepareWatchlistSuccessView(WatchlistOutputData data) {
        WatchlistState newState = new WatchlistState();
        newState.setUsername(data.getUsername());
        newState.setPassword(data.getPassword());
        newState.setSymbols(data.getSymbols());
        newState.setPrices(data.getPrices());
        newState.setPerformance(data.getPerformance());

        viewModel.setState(newState);
        viewModel.firePropertyChange();

        viewManager.setState(viewModel.getViewName());
        viewManager.firePropertyChange();
    }

    @Override
    public void prepareWatchlistFailView(String error) {
        final LoggedInState loginState = loggedInVM.getState();
        loginState.setLoggedInError(error);
        loggedInVM.firePropertyChange();
    }
}
