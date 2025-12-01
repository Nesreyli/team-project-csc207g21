package use_case.loggedIn;

public class LoggedInInteractor implements LoggedInInputBoundary {
    private final LoggedInOutputBoundary loggedInOutputBoundary;

    public LoggedInInteractor(LoggedInOutputBoundary loggedInOutputBoundary) {
        this.loggedInOutputBoundary = loggedInOutputBoundary;
    }

    /**
     * Executes the action to switch to the search view.
     * This method delegates the navigation to the output boundary.
     */
    public void switchToSearch() {
        loggedInOutputBoundary.switchToSearch();
    }
}
