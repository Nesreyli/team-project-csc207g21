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

    /**
     * Getter.
     * @return priceState
     */
    public PriceState getPriceState() {
        return priceState;
    }

    /**
     * Getter.
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Getter.
     * @return isBuy
     */
    public Boolean getIsBuy() {
        return isBuy;
    }
}
