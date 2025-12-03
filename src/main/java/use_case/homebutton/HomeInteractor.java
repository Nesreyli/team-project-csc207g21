package use_case.homebutton;

public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homeOutputBoundary;

    public HomeInteractor(HomeOutputBoundary homeOutputBoundary) {
        this.homeOutputBoundary = homeOutputBoundary;
    }

    /**
     * Execute previous.
     * @param username the username of the currently logged-in user
     * @param password the password associated with the user
     */
    @Override
    public void executePrevious(String username, String password) {
        final HomeOutputData homeOutputData = new HomeOutputData(username, password);
        homeOutputBoundary.preparePreviousView(homeOutputData);
    }
}
