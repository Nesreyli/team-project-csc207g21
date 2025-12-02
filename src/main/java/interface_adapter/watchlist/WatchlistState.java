package interface_adapter.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * State object for the Watchlist use case.
 */
public class WatchlistState {
    private String username = "";
    private String password = "";
    private List<String> symbols;
    private Map<String, BigDecimal> prices;
    private Map<String, BigDecimal> performance;
    private String message;

    /**
     * @return the stored username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to store
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the stored password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to store
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the list of stock symbols
     */
    public List<String> getSymbols() {
        return symbols;
    }

    /**
     * @param symbols the stock symbols to store
     */
    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    /**
     * @return the map of stock prices
     */
    public Map<String, BigDecimal> getPrices() {
        return prices;
    }

    /**
     * @param prices the stock prices to store
     */
    public void setPrices(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    /**
     * @return the performance values
     */
    public Map<String, BigDecimal> getPerformance() {
        return performance;
    }

    /**
     * @param performance the performance values to store
     */
    public void setPerformance(Map<String, BigDecimal> performance) {
        this.performance = performance;
    }

    /**
     * @return the stored message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to store
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
