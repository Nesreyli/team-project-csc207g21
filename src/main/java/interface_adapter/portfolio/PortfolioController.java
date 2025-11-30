package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;

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
