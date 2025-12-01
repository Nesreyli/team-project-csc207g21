package use_case.stock_price;

import entity.Price;

public interface PriceOutputBoundary {
    /**
     * Prepares the view for a successful stock price retrieval.
     * @param price the Price entity containing the stock price information
     */
    void prepareSuccessView(Price price);

    /**
     * Prepares the view when the stock price retrieval fails.
     * @param message a message explaining why the retrieval failed
     */
    void prepareFailView(String message);
}
