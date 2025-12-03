package use_case.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The Output Data for the Watchlist Use Case.
 */

public class WatchlistOutputData {

    private final String username;
    private final String password;
    private final List<String> symbols;
    private final Map<String, BigDecimal> prices;
    private final Map<String, BigDecimal> performance;
    private final String message;

    /**
     * Constructs a WatchlistOutputData object.
     *
     * @param username    the username of the user
     * @param password    the password of the user
     * @param symbols     list of stock symbols in the watchlist
     * @param prices      mapping of symbols to their prices
     * @param performance mapping of symbols to their performance
     * @param message     the message received from server
     */
    public WatchlistOutputData(String username, String password,
                               List<String> symbols,
                               Map<String, BigDecimal> prices,
                               Map<String, BigDecimal> performance,
                               String message) {
        this.username = username;
        this.password = password;
        this.symbols = symbols;
        this.prices = prices;
        this.performance = performance;
        this.message = message;
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

    public String getMessage() {
        return message;
    }
}
