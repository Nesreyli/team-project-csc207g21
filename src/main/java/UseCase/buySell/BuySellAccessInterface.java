package UseCase.buySell;

import InterfaceAdapter.stock_price.PriceState;

public interface BuySellAccessInterface {
    public Entity.Response setStockData(PriceState priceState, Integer amount, Boolean isBuy);
}
