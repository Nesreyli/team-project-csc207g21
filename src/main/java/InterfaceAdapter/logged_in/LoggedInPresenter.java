package InterfaceAdapter.logged_in;

import InterfaceAdapter.Search.SearchState;
import InterfaceAdapter.Search.SearchViewModel;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.login.LoginViewModel;
import UseCase.loggedIn.LoggedInOutputBoundary;

import javax.swing.text.View;

public class LoggedInPresenter implements LoggedInOutputBoundary {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;

    public LoggedInPresenter(LoggedInViewModel loggedInViewModel,
                             ViewManagerModel viewManagerModel,
                             SearchViewModel searchViewModel){
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    public void switchToSearch() {
        LoggedInState loggedInState = loggedInViewModel.getState();

        searchViewModel.setState(new SearchState());
        SearchState searchState = searchViewModel.getState();
        searchState.setUsername(loggedInState.getUsername());
        searchState.setPassword(loggedInState.getPassword());

        searchViewModel.setState(new SearchState());

        loggedInViewModel.firePropertyChange();
        searchViewModel.firePropertyChange();

        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
