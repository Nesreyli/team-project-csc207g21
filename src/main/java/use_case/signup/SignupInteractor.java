package use_case.signup;

import entity.Response;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if ("".equals(signupInputData.getPassword())) {
            userPresenter.prepareFailView("New password cannot be empty");
        }
        else if (signupInputData.getPassword().length() < 5) {
            userPresenter.prepareFailView("Password too weak");
        }
        else if ("".equals(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username cannot be empty");
        }
        else {
            Response response = userDataAccessObject.signUp(signupInputData.getUsername(),
                    signupInputData.getPassword());
            switch(response.getStatus_code()){
                case 200:
                    userPresenter.prepareSuccessView();
                    break;
                case 400:
                    userPresenter.prepareFailView("User already exists");
                    break;
                default:
                    userPresenter.prepareFailView("Server Error");
            }
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
