package use_case.buySell;

import interface_adapter.stock_price.PriceState;

/**
 * The Input Data for the Buy/Sell Use Case.
 */
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
