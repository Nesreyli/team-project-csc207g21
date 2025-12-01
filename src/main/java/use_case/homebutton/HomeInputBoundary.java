package use_case.homebutton;

public interface HomeInputBoundary {
    /**
     * Executes the action of returning to the previous view or state.
     *
     * @param username the username of the currently logged-in user
     * @param password the password associated with the user
     */
    void executePrevious(String username, String password);
}
