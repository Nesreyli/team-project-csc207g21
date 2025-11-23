package Entity;

import java.math.BigDecimal;
import java.util.Map;

public class Price {
    private String symbol;
    private BigDecimal price;
    private BigDecimal performance;
    private BigDecimal ytdPrice;

    public Price(){}

    public Price(String s, BigDecimal p, BigDecimal performance, BigDecimal ytdPrice){
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
        private final Price price;
        public Builder(){
            price = new Price();
        }
        public Price.Builder symbol(String symbol){
            price.symbol = symbol;
            return this;
        }
        public Price.Builder price(BigDecimal price){
            this.price.price = price;
            return this;
        }
        public Price.Builder performance(BigDecimal performance){
            this.price.performance = performance;
            return this;
        }
        public Price.Builder value(BigDecimal ytdPrice){
            this.price.ytdPrice = ytdPrice;
            return this;
        }
        public Price build() {
            return price;
        }
    }

}
