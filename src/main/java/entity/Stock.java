package Entity;

import java.math.BigDecimal;

public class Stock {
    private String symbol;
    private BigDecimal price;
    private BigDecimal performance;
    private BigDecimal ytdPrice;

    public Stock(){}

    public Stock(String s, BigDecimal p, BigDecimal performance, BigDecimal ytdPrice){
        symbol = s;
        price = p;
        this.performance = performance;
        this.ytdPrice = ytdPrice;
    }
    public String getSymbol(){
        return symbol;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public BigDecimal getYtdPrice() {
        return ytdPrice;
    }

    public static class Builder{
        private final Stock stock;
        public Builder(){
            stock = new Stock();
        }
        public Stock.Builder symbol(String symbol){
            stock.symbol = symbol;
            return this;
        }
        public Stock.Builder price(BigDecimal price){
            this.stock.price = price;
            return this;
        }
        public Stock.Builder performance(BigDecimal performance){
            this.stock.performance = performance;
            return this;
        }
        public Stock.Builder value(BigDecimal ytdPrice){
            this.stock.ytdPrice = ytdPrice;
            return this;
        }
        public Stock build() {
            return stock;
        }
    }

}
