package use_case.portfolio;

import entity.Portfolio;
import entity.Price;
import entity.Response;
import use_case.stock_price.PriceAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class PortfolioInteractor implements PortfolioInputBoundary {
    private final static int SUCCESS_CODE = 200;
    private final static int NAME_ERROR = 400;
    private final static int ERROR_CODE = 500;
    private PortfolioAccessInterface portfolioDb;
    private PriceAccessInterface priceDb;
    private PortfolioOutputBoundary portfolioOutput;

    public PortfolioInteractor(PortfolioAccessInterface portfolio,
                               PriceAccessInterface priceDb,
                               PortfolioOutputBoundary port) {
        portfolioDb = portfolio;
        this.priceDb = priceDb;
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
            case SUCCESS_CODE:
                Map<String, Price> prices = getPrices(response);
                if (prices != null) {
                    final PortfolioOutputData portOutputData =
                            PortfolioOutputDataFactory.create((Portfolio) response.getEntity(),
                                    prices);
                    portfolioOutput.preparePortSuccessView(portOutputData);
                    break;
                }
            case NAME_ERROR:
                portfolioOutput.preparePortFailView("Incorrect username or password.");
                break;
            case ERROR_CODE:
                portfolioOutput.preparePortFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }

    private Map<String, Price> getPrices(Response response) {
        final Map<String, Price> result = new HashMap<>();
        final Map<String, Object> portfolio = ((Portfolio)response.getEntity()).getHoldings();
        for (String symbol: portfolio.keySet()) {
            final entity.Response responsePrice = priceDb.getPrice(symbol);

            switch(responsePrice.getStatus_code()) {
                case SUCCESS_CODE:
                    result.put(symbol, (Price) responsePrice.getEntity());
                    break;
                case NAME_ERROR:
                    return null;
            }
        }
        return result;
    }
}
