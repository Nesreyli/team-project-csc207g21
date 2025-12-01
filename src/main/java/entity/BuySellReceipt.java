package entity;

import java.math.BigDecimal;

public class BuySellReceipt {
    private Character order;
    private String symbol;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public BuySellReceipt() {

    }

    public BuySellReceipt(Character order, String symbol, Integer amount, BigDecimal price, BigDecimal totalPrice) {
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

    public static class Builder {
        private final BuySellReceipt receipt;

        public Builder() {
            receipt = new BuySellReceipt();
        }

        /**
         * Sets whether the transaction is a buy or sell order.
         * @param order a character representing the order type (e.g., 'B' for buy, 'S' for sell)
         * @return this builder instance for method chaining
         */
        public BuySellReceipt.Builder order(Character order) {
            receipt.order = order;
            return this;
        }

        /**
         * Sets the number of units involved in the transaction.
         * @param amount the quantity of the asset being bought or sold
         * @return this builder instance for method chaining
         */
        public BuySellReceipt.Builder amount(Integer amount) {
            receipt.amount = amount;
            return this;
        }

        /**
         * Sets the trading symbol associated with the transaction.
         * @param symbol the asset's ticker symbol (e.g., "AAPL", "TSLA")
         * @return this builder instance for method chaining
         */
        public BuySellReceipt.Builder symbol(String symbol) {
            receipt.symbol = symbol;
            return this;
        }

        /**
         * Sets the price per unit for the transaction.
         *
         * @param price the price of one unit as a BigDecimal
         * @return this builder instance for method chaining
         */
        public BuySellReceipt.Builder price(BigDecimal price) {
            receipt.price = price;
            return this;
        }

        /**
         * Sets the total price of the transaction.
         * @param totalPrice the total transaction cost as a BigDecimal
         * @return this builder instance for method chaining
         */
        public BuySellReceipt.Builder totalPrice(BigDecimal totalPrice) {
            receipt.totalPrice = totalPrice;
            return this;
        }

        /**
         * Builds and returns the fully constructed BuySellReceipt.
         * @return the completed BuySellReceipt instance
         */
        public BuySellReceipt build() {
            return receipt;
        }
    }
}
