package InterfaceAdapter.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WatchlistState {
    private String username = "";
    private String password = "";
    private List<String> symbols;
    private Map<String, BigDecimal> prices;
    private Map<String, BigDecimal> performance;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }
    public Map<String, BigDecimal> getPrices() { return prices; }
    public void setPrices(Map<String, BigDecimal> prices) { this.prices = prices; }
    public Map<String, BigDecimal> getPerformance() { return performance; }
    public void setPerformance(Map<String, BigDecimal> performance) { this.performance = performance; }
}
