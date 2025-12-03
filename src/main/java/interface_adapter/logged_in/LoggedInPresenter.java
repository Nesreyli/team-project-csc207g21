package interface_adapter.logged_in;

import interface_adapter.Stock_Search.SearchState;
import interface_adapter.Stock_Search.SearchViewModel;
import interface_adapter.ViewManagerModel;
import use_case.loggedIn.LoggedInOutputBoundary;

public class LoggedInPresenter implements LoggedInOutputBoundary {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;

    public LoggedInPresenter(LoggedInViewModel loggedInViewModel,
                             ViewManagerModel viewManagerModel,
                             SearchViewModel searchViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    public void switchToSearch() {
        final LoggedInState loggedInState = loggedInViewModel.getState();

        final SearchState searchState = searchViewModel.getState();
        searchState.setUsername(loggedInState.getUsername());
        searchState.setPassword(loggedInState.getPassword());

        loggedInViewModel.setState(new LoggedInState());
        loggedInViewModel.firePropertyChange();
        searchViewModel.firePropertyChange();

        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
