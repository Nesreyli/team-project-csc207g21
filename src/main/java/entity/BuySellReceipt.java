package Entity;

import java.math.BigDecimal;

public class BuySellReceipt {
    private Character order;
    private String symbol;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public BuySellReceipt() {}

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

    public static class Builder{
        private final BuySellReceipt receipt;

        public Builder(){
            receipt = new BuySellReceipt();
        }

        public BuySellReceipt.Builder order(Character order){
            receipt.order = order;
            return this;
        }

        public BuySellReceipt.Builder amount(Integer amount){
            receipt.amount = amount;
            return this;
        }

        public BuySellReceipt.Builder symbol(String symbol){
            receipt.symbol = symbol;
            return this;
        }

        public BuySellReceipt.Builder price(BigDecimal price){
            receipt.price = price;
            return this;
        }

        public BuySellReceipt.Builder totalPrice(BigDecimal totalPrice){
            receipt.totalPrice = totalPrice;
            return this;
        }

        public BuySellReceipt build() {
            return receipt;
        }
    }
}
