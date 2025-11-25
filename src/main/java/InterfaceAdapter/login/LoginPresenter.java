package InterfaceAdapter.login;

import InterfaceAdapter.usersession.UserSessionViewModel;
import UseCase.login.LoginOutputBoundary;
import UseCase.login.LoginOutputData;

import javax.swing.SwingUtilities;

public class LoginPresenter implements LoginOutputBoundary {

    private final UserSessionViewModel userSessionViewModel;
    private final LoginViewModel loginViewModel;

    public LoginPresenter(UserSessionViewModel userSessionViewModel,
                          LoginViewModel loginViewModel) {
        this.userSessionViewModel = userSessionViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        SwingUtilities.invokeLater(() -> {
            userSessionViewModel.login(response.getUsername(), response.getPassword());
            loginViewModel.setState(new LoginViewModel.LoginState());
            loginViewModel.firePropertyChange();
        });
    }

    @Override
    public void prepareFailView(String error) {
        SwingUtilities.invokeLater(() -> {
            LoginViewModel.LoginState state = loginViewModel.getState();
            state.setLoginError(error);
            loginViewModel.firePropertyChange();
        });
    }
}
