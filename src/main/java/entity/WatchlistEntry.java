package entity;

import java.math.BigDecimal;

/**
 * A simple Entity representing the Watchlist Entries.
 */

public class WatchlistEntry {
    private String symbol;
    private BigDecimal price;
    private BigDecimal performance;

    public WatchlistEntry(String symbol, BigDecimal price, BigDecimal performance) {
        this.symbol = symbol;
        this.price = price;
        this.performance = performance;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    // NEW
    public BigDecimal getPerformance() {
        return performance;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }
}
