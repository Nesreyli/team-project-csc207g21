package interface_adapter.buySell;

import java.math.BigDecimal;

/**
 * The State information representing the receipt of a buy/sell transaction.
 */
public class BuySellState {
    private Character order;
    private String symbol;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String errorMessage;

    public Character getOrder() {
        return order;
    }

    public void setOrder(Character order) {
        this.order = order;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
