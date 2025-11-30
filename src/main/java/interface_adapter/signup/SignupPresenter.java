package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView() {
        // On success, switch to the login view.
        switchToLoginView();
    }

    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.error(error);
        signupViewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        signupViewModel.setState(new SignupState());
        signupViewModel.firePropertyChange();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
