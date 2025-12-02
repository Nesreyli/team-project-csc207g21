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
     * Fetches username.
     * @return the stored username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     * @param username the username to store
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Fetches password.
     * @return the stored password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     * @param password the password to store
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Fetches list of stock symbols.
     * @return the list of stock symbols
     */
    public List<String> getSymbols() {
        return symbols;
    }

    /**
     * Sets the list of stock symbols.
     * @param symbols the stock symbols to store
     */
    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    /**
     * Fetches current prices of the symbols.
     * @return the map of stock prices
     */
    public Map<String, BigDecimal> getPrices() {
        return prices;
    }

    /**
     * Sets the current prices of the symbols.
     * @param prices the stock prices to store
     */
    public void setPrices(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    /**
     * Fetches current performance of stock symbols.
     * @return the performance values
     */
    public Map<String, BigDecimal> getPerformance() {
        return performance;
    }

    /**
     * Sets current performance of the stock symbols.
     * @param performance the performance values to store
     */
    public void setPerformance(Map<String, BigDecimal> performance) {
        this.performance = performance;
    }

    /**
     * Fetches message from the server.
     * @return the stored message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message sent to another function.
     * @param message the message to store
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
