package use_case.stock_price;

import entity.Price;
import entity.Response;

public class StockPriceInteractor implements PriceInputBoundary {
    private PriceAccessInterface priceAccessInterface;
    private PriceOutputBoundary priceOutputBoundary;

    public StockPriceInteractor(PriceAccessInterface priceAccessInterface,
                                PriceOutputBoundary priceOutputBoundary) {
        this.priceAccessInterface = priceAccessInterface;
        this.priceOutputBoundary = priceOutputBoundary;
    }

    /**
     * Executes the Stock Price use case for the given symbol.
     * Retrieves the stock price from the data access interface and sends the
     * results to the output boundary. Handles both success and failure scenarios.
     *
     * @param symbol the stock symbol for which to retrieve the price
     */
    public void execute(String symbol) {
        final Response response = priceAccessInterface.getPrice(symbol);

        switch (response.getStatus_code()) {
            case 200:
                priceOutputBoundary.prepareSuccessView((Price) response.getEntity());
                break;
            case 400:
                priceOutputBoundary.prepareFailView("Server Error");
        }
    }
}
