package use_case.loggedIn;

public interface LoggedInOutputBoundary {
    /**
     * Handles the action to switch to the search view.
     * This method is called by the interactor to update the UI or
     * perform navigation to the search screen.
     */
    void switchToSearch();
}
