package InterfaceAdapter.logout;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.login.LoginViewModel;
import UseCase.logout.LogoutOutputBoundary;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                           LoginViewModel loginViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView() {
        // We need to switch to the login view, which should have
        // an empty username and password.

        // We also need to set the username in the LoggedInState to
        // the empty string.
        // 1. get the LoggedInState out of the appropriate View Model,
        // 2. set the username in the state to the empty string
        // 3. firePropertyChanged so that the View that is listening is updated.
        loggedInViewModel.setState(new LoggedInState());
        loggedInViewModel.firePropertyChange();

        // 1. get the LoginState out of the appropriate View Model,
        // 2. set the username in the state to be the username of the user that just logged out,
        // 3. firePropertyChanged so that the View that is listening is updated.
        loginViewModel.firePropertyChange();

        // This code tells the View Manager to switch to the LoginView.
        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
}
