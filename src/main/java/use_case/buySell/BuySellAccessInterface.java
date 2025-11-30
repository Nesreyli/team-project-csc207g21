package use_case.buySell;

import interface_adapter.stock_price.PriceState;

public interface BuySellAccessInterface {
    public entity.Response setStockData(PriceState priceState, Integer amount, Boolean isBuy);
}
