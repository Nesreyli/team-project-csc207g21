package Entity;

import java.math.BigDecimal;

public class Price {
    private String symbol;
    private BigDecimal price;
    private BigDecimal ytdPrice;
    private BigDecimal ytdPerformance;


    public Price() {//this.market_cap = market_cap;
    }

    public String getSymbol() { return symbol; }

    public BigDecimal getYtdPrice() { return ytdPrice; }

    public BigDecimal getPrice() { return price; }

    public BigDecimal getYtdPerformance() { return ytdPerformance; }

    public static class Builder{
        private Price price;
        public Builder(){
            price = new Price();
        }

        public Builder addSymbol(String symbol){
            price.symbol = symbol;
            return this;
        }

        public Builder addPrice(BigDecimal price){
            this.price.price = price;
            return this;
        }

        public Builder addYtdPrice(BigDecimal ytd){
            price.ytdPrice = ytd;
            return this;
        }

        public Builder addYtdPerformance(BigDecimal perf){
            price.ytdPerformance = perf;
            return this;
        }

        public Price build(){
            return price;
        }
    }
}
