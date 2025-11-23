package UseCase.stock;

import java.math.BigDecimal;

public class StockOutputData {
    private final BigDecimal price;
    private final String symbol;
    private final BigDecimal ytdprice;
    private final BigDecimal performance;


    public StockOutputData(BigDecimal price, String symbol,
                           BigDecimal ytdprice, BigDecimal performance) {
        this.price = price;
        this.symbol = symbol;
        this.ytdprice = ytdprice;
        this.performance = performance;
    }

    public BigDecimal getPrice() { return price; }

    public BigDecimal getYtdprice() {
        return ytdprice;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public String getSymbol() { return symbol; }
}
