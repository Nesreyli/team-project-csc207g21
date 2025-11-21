package InterfaceAdapter.logged_in;

import UseCase.portfolio.PortfolioInputBoundary;
import UseCase.portfolio.PortfolioInputData;

public class LoggedInController {
    private PortfolioInputBoundary portInteractor;

    public LoggedInController(PortfolioInputBoundary port){
        portInteractor = port;
    }

    public void portExecute(String username, String password){
        PortfolioInputData input = new PortfolioInputData(username, password);
        portInteractor.execute(input);
    }

}
