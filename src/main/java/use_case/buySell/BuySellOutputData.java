package use_case.buySell;

import java.math.BigDecimal;

/**
 * The Output Data for the Login Use Case.
 */
public class BuySellOutputData {
    private final Character order;
    private final String symbol;
    private final Integer amount;
    private final BigDecimal price;
    private final BigDecimal totalPrice;

    public BuySellOutputData(Character order, String symbol, Integer amount, BigDecimal price, BigDecimal totalPrice) {
        this.order = order;
        this.symbol = symbol;
        this.amount = amount;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    /**
     * Getter.
     * @return order
     */
    public Character getOrder() {
        return order;
    }

    /**
     * Getter.
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
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
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Getter.
     * @return totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
