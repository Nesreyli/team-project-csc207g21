package UseCase.portfolio;

import DataAccess.PortfolioAccessObject;
import Entity.Portfolio;

import java.util.concurrent.Executors;

public class PortfolioInteractor implements PortfolioInputBoundary {

    private final PortfolioAccessObject dao;
    private final PortfolioOutputBoundary portfolioOutputBoundary;

    public PortfolioInteractor(PortfolioAccessObject dao, PortfolioOutputBoundary portfolioOutputBoundary) {
        this.dao = dao;
        this.portfolioOutputBoundary = portfolioOutputBoundary;
    }
    public void execute(PortfolioInputData input) {
        // Run on a background thread so Swing UI doesn't freeze
        Executors.newSingleThreadExecutor().submit(() -> {
            Entity.Response response = dao.getPort(input.getUsername(), input.getPassword());
            switch (response.getStatus_code()) {
                case 200:
                    PortfolioOutputData portfolioOutputData = new PortfolioOutputData(((Portfolio) response.getEntity()).getUser().getName(),
                            ((Portfolio) response.getEntity()).getUser().getPassword(), ((Portfolio) response.getEntity()).getCash(),
                            ((Portfolio) response.getEntity()).getHoldings(), ((Portfolio) response.getEntity()).getValue(),
                            ((Portfolio) response.getEntity()).getPerformance());

                    portfolioOutputBoundary.prepareSuccessView(portfolioOutputData);
                    break;
                case 400:
                    portfolioOutputBoundary.prepareFailView("Incorrect username or password.");
                    break;
                default:
                    portfolioOutputBoundary.prepareFailView("Error");
                    break;
            }})
        ;
    }
}
