package use_case.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WatchlistOutputData {
    private final String username;
    private final String password;
    private final List<String> symbols;
    private final Map<String, BigDecimal> prices;
    private final Map<String, BigDecimal> performance;

    public WatchlistOutputData(String username, String password,
                               List<String> symbols,
                               Map<String, BigDecimal> prices,
                               Map<String, BigDecimal> performance) {
        this.username = username;
        this.password = password;
        this.symbols = symbols;
        this.prices = prices;
        this.performance = performance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public Map<String, BigDecimal> getPrices() {
        return prices;
    }

    public Map<String, BigDecimal> getPerformance() {
        return performance;
    }
}
