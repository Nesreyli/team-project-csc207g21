package InterfaceAdapter.watchlist;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import UseCase.watchlist.WatchlistOutputBoundary;
import UseCase.watchlist.WatchlistOutputData;

import java.util.Collections;

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
