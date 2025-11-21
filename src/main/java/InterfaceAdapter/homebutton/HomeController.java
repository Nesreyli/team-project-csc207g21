package InterfaceAdapter.homebutton;

import UseCase.homebutton.HomeInputBoundary;
import UseCase.logout.LogoutInputBoundary;

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
        homeInteractor.execute(username, password);
    }
}
