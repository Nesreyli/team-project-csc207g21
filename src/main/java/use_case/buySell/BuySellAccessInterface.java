package use_case.buySell;

import interface_adapter.stock_price.PriceState;

public interface BuySellAccessInterface {
    /**
     * Executes a buy or sell transaction based on the provided parameters.
     *
     * @param priceState the current price information needed to evaluate the order
     * @param amount     the number of shares to buy or sell
     * @param isBuy      true for a buy order, false for a sell order
     * @return a Response object containing the status code and any resulting entity,
     *         such as a BuySellReceipt
     */

    entity.Response setStockData(PriceState priceState, Integer amount, Boolean isBuy);
}
