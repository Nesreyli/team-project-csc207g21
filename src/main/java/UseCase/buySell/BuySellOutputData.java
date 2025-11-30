package UseCase.buySell;

import java.math.BigDecimal;

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

    public Character getOrder() {
        return order;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
