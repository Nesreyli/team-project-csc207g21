package interface_adapter.portfolio;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The State information representing the logged-in user.
 */
public class PortfolioState {
    private String username = "";

    private String password = "";

    private String value = "";

    private String cash = "";

    private String performance = "";

    private Map<String, Object> holdings;

    private Map<String, BigDecimal> prices;

    private Map<String, BigDecimal> stockPerformance;

    private String mostValue;

    private String bestPerf;

    public PortfolioState(PortfolioState copy) {
        username = copy.username;
        password = copy.password;
        value = copy.value;
        performance = copy.performance;
        cash = copy.cash;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public PortfolioState() {
    }

    public Map<String, BigDecimal> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    public Map<String, BigDecimal> getStockPerformance() {
        return stockPerformance;
    }

    public void setStockPerformances(Map<String, BigDecimal> stockPerformance) {
        this.stockPerformance = stockPerformance;
    }

    public String getUsername() {
        return username;
    }

    public String getCash() {
        return cash;
    }

    public String getPerformance() {
        return performance;
    }

    public String getValue() {
        return value;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Object> holdings) {
        this.holdings = holdings;
    }

    public String getPassword() {
        return password;
    }

    public String getBestPerf() {
        return bestPerf;
    }

    public void setBestPerf(String bestPerf) {
        this.bestPerf = bestPerf;
    }

    public String getMostValue() {
        return mostValue;
    }

    public void setMostValue(String mostValue) {
        this.mostValue = mostValue;
    }

    public void setStockPerformance(Map<String, BigDecimal> stockPerformance) {
        this.stockPerformance = stockPerformance;
    }
}