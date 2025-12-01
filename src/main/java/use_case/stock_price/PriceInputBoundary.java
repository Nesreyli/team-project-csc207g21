package use_case.stock_price;

public interface PriceInputBoundary {
    /**
     * Executes the Stock Price use case for the given stock symbol.
     * @param symbol the stock symbol for which to retrieve the price
     */
    void execute(String symbol);
}
