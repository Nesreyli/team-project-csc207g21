package interface_adapter.homebutton;

import use_case.homebutton.HomeInputBoundary;

/**
 * The controller for the Logout Use Case.
 */
public class HomeController {

    private HomeInputBoundary homeInteractor;

    public HomeController(HomeInputBoundary homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    /**
     * Executes the Logout Use Case.
     */
    public void execute(String username, String password) {
        homeInteractor.executePrevious(username, password);
    }
}
