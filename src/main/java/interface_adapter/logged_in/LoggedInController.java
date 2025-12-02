package interface_adapter.logged_in;

import use_case.loggedIn.LoggedInInputBoundary;

/**
 * Controller responsible for handling user actions after the user has logged in.
 */
public class LoggedInController {
    private LoggedInInputBoundary loggedInInputBoundary;

    public LoggedInController(LoggedInInputBoundary loggedInInputBoundary) {
        this.loggedInInputBoundary = loggedInInputBoundary;
    }

    public void switchToSearch() {
        loggedInInputBoundary.switchToSearch();
    }
}
