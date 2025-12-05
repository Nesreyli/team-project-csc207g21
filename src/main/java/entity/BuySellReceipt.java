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

    public static class Builder {
        private final BuySellReceipt receipt;

        public Builder() {
            receipt = new BuySellReceipt();
        }

        /**
<<<<<<< HEAD
         * Sets order.
         * @param order order
         * @return this
         */
        public BuySellReceipt.Builder order(Character order){
            receipt.order = order;
            return this;
        }

        /**
<<<<<<< HEAD
         * Sets amount.
         * @param amount amonut
         * @return this
         */
        public BuySellReceipt.Builder amount(Integer amount){
            receipt.amount = amount;
            return this;
        }

        /**
<<<<<<< HEAD
         * Sets symbol
         * @param symbol symbol
         * @return this
         */
        public BuySellReceipt.Builder symbol(String symbol){
            receipt.symbol = symbol;
            return this;
        }

        /**
<<<<<<< HEAD
         * Sets price.
         * @param price price
         * @return this
         */
        public BuySellReceipt.Builder price(BigDecimal price){
            receipt.price = price;
            return this;
        }

        /**
<<<<<<< HEAD
         * Sets totalPrice.
         * @param totalPrice price
         * @return this
         */
        public BuySellReceipt.Builder totalPrice(BigDecimal totalPrice){
            receipt.totalPrice = totalPrice;
            return this;
        }

        /**
<<<<<<< HEAD
         * Returns receipt.
         * @return receipt
=======
         * Builds and returns the fully constructed BuySellReceipt.
         * @return the completed BuySellReceipt instance
>>>>>>> origin/main
         */
        public BuySellReceipt build() {
            return receipt;
        }
    }
}
