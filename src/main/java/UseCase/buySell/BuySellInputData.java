package UseCase.buySell;

import InterfaceAdapter.stock_price.PriceState;

public class BuySellInputData {
    private final PriceState priceState;
    private final Integer amount;
    private final Boolean isBuy;

    public BuySellInputData(PriceState priceState, Integer amount, Boolean isBuy) {
        this.priceState = priceState;
        this.amount = amount;
        this.isBuy = isBuy;
    }

    public PriceState getPriceState() {
        return priceState;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean getIsBuy() {
        return isBuy;
    }
}