package use_case.login;

import entity.User;

public class LogInInteractor implements LoginInputBoundary {
    private final LogInAccessInterface userAccessObject;
    private final LoginOutputBoundary loginOutputBoundary;

    public LogInInteractor(LogInAccessInterface userAccessObject,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userAccessObject = userAccessObject;
        this.loginOutputBoundary = loginOutputBoundary;
    }

    /**
     * Executes the Login use case using the provided input data.
     * <p>
     * The interactor validates the username and password through the
     * data access interface and communicates the result via the output boundary.
     *
     * @param input the data containing the username and password for login
     */
    public void execute(LoginInputData input) {
        final entity.Response response = userAccessObject.logIn(input.getUsername(), input.getPassword());
        switch (response.getStatus_code()) {
            case 200:
                final LoginOutputData loginOutputData = new LoginOutputData(((User) response.getEntity()).getName(),
                        ((User) response.getEntity()).getPassword());
                loginOutputBoundary.prepareSuccessView(loginOutputData);
                break;
            case 400:
                loginOutputBoundary.prepareFailView("Incorrect username or password.");
                break;
            default:
                loginOutputBoundary.prepareFailView("Error");
                break;
        }
    }

    @Override
    public void toSignup() {
        loginOutputBoundary.prepareSignupView();
    }
}
