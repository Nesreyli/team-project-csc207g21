package use_case.portfolio;

import entity.Portfolio;

public class PortfolioInteractor implements PortfolioInputBoundary {
    private PortfolioAccessInterface portfolioDb;
    private PortfolioOutputBoundary portfolioOutput;

    public PortfolioInteractor(PortfolioAccessInterface portfolio, PortfolioOutputBoundary port) {
        portfolioDb = portfolio;
        portfolioOutput = port;
    }

    /**
     * Executes the Portfolio use case using the provided input data.
     * Retrieves the user's portfolio from the data access interface and sends
     * the results to the output boundary. Handles success, failure, and server error
     * scenarios.
     * @param input the data containing the username and password for fetching the portfolio
     */
    public void execute(PortfolioInputData input) {
        final entity.Response response = portfolioDb.getPort(input.getUsername(), input.getPassword());
        switch (response.getStatus_code()) {
            case 200:
                final PortfolioOutputData portOutputData =
                        PortfolioOutputDataFactory.create((Portfolio) response.getEntity());
                portfolioOutput.preparePortSuccessView(portOutputData);
                break;
            case 400:
                portfolioOutput.preparePortFailView("Incorrect username or password.");
                break;
            case 500:
                portfolioOutput.preparePortFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }
}
