package InterfaceAdapter.portfolio;

import UseCase.portfolio.PortfolioInputBoundary;
import UseCase.portfolio.PortfolioInputData;

public class PortfolioController {

    private final PortfolioInputBoundary portfolioInteractor;

    public PortfolioController(PortfolioInputBoundary portfolioInteractor){
        this.portfolioInteractor = portfolioInteractor;
    }

    public void execute(String username, String password) {
        final PortfolioInputData portfolioInputData = new PortfolioInputData(
                username, password);

        portfolioInteractor.execute(portfolioInputData);
    }
}
