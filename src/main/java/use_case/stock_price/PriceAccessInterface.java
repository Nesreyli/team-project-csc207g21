package use_case.stock_price;

import entity.Response;

public interface PriceAccessInterface {
    /**
     * Retrieves the current price of a given stock symbol.
     * @param symbol the stock symbol to retrieve the price for
     * @return a  Response object containing the stock price or information
     *         about why the retrieval failed
     */
    Response getPrice(String symbol);
}
