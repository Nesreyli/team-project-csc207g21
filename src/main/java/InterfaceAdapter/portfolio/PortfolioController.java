package InterfaceAdapter.logged_in;

import UseCase.portfolio.PortfolioInputBoundary;
import UseCase.portfolio.PortfolioInputData;

public class PortfolioController {
    private PortfolioInputBoundary portInteractor;

    public PortfolioController(PortfolioInputBoundary port){
        portInteractor = port;
    }

    public void portExecute(String username, String password){
        PortfolioInputData input = new PortfolioInputData(username, password);
        portInteractor.execute(input);
    }

}
