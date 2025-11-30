package entity;

import java.math.BigDecimal;

public class WatchlistEntry {
    private String symbol;
    private BigDecimal price;
    private BigDecimal performance;

    public WatchlistEntry(String symbol, BigDecimal price, BigDecimal performance) {
        this.symbol = symbol;
        this.price = price;
        this.performance = performance;
    }

    public String getSymbol() { return symbol; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getPerformance() { return performance; } // NEW

    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setPerformance(BigDecimal performance) { this.performance = performance; }
}
