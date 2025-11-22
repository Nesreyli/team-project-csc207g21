package InterfaceAdapter.logout;

import UseCase.logout.LogoutInputBoundary;

/**
 * The controller for the Logout Use Case.
 */
public class LogoutController {

    private LogoutInputBoundary logoutUseCaseInteractor;

    public LogoutController(LogoutInputBoundary logoutInputBoundary) {
        this.logoutUseCaseInteractor = logoutInputBoundary;
    }

    /**
     * Executes the Logout Use Case.
     */
    public void execute() {
        logoutUseCaseInteractor.execute();
    }
}
