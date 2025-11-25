package InterfaceAdapter.logged_in;

import InterfaceAdapter.ViewModel;
import InterfaceAdapter.portfolio.PortfolioController;

/**
 * The View Model for the Logged In View.
 */
public class LoggedInViewModel extends ViewModel<LoggedInState> {
    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());

    }

    private PortfolioController portfolioController;

    public void setPortfolioController(PortfolioController controller) {
        this.portfolioController = controller;
    }
}
