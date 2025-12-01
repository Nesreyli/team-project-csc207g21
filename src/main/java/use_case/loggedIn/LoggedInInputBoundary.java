package use_case.loggedIn;

public interface LoggedInInputBoundary {
    /**
     * Executes the action to switch to the search view.
     * Implementations should delegate any necessary processing to the output boundary.
     */
    void switchToSearch();
}
