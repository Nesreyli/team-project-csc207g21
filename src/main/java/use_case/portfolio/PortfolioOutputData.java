package use_case.portfolio;

import java.math.BigDecimal;
import java.util.Map;

public class PortfolioOutputData {
    private final String username;
    private final String password;
    private final BigDecimal cash;
    private final Map<String, Object> holdings;
    private final BigDecimal value;
    private final BigDecimal performance;

    public PortfolioOutputData(String username, String password, BigDecimal cash,
                               Map<String, Object> holdings, BigDecimal value, BigDecimal perf) {
        this.username = username;
        this.password = password;
        this.cash = cash;
        this.holdings = holdings;
        this.value = value;
        this.performance = perf;
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
}
