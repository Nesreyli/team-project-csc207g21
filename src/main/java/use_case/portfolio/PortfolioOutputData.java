package use_case.portfolio;

import entity.Price;

import java.math.BigDecimal;
import java.util.Map;

public class PortfolioOutputData {
    private final String username;
    private final String password;
    private final BigDecimal cash;
    private final Map<String, Object> holdings;
    private Map<String, BigDecimal> prices;
    private Map<String, BigDecimal> stockPerformance;    private final BigDecimal value;
    private final BigDecimal performance;

    public PortfolioOutputData(String username, String password, BigDecimal cash,
                               Map<String, Object> holdings, BigDecimal value,
                               BigDecimal perf, Map<String, BigDecimal> prices,
                               Map<String, BigDecimal> stockPerformance) {
        this.username = username;
        this.password = password;
        this.cash = cash;
        this.holdings = holdings;
        this.value = value;
        this.performance = perf;
        this.prices = prices;
        this.stockPerformance = stockPerformance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public Map<String, Object> getHoldings() {
        return holdings;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public Map<String, BigDecimal> getPrices() {return prices;}

    public Map<String, BigDecimal> getStockPerformance() {return stockPerformance;}
}
