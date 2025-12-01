package use_case.login;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    /**
     * Navigates to the signup screen.
     * Implementations should delegate the necessary UI or navigation logic
     * to the output boundary.
     */
    void toSignup();
}
