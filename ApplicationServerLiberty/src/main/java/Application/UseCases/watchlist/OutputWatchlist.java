package Application.UseCases.watchlist;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequestScoped
public class OutputWatchlist {
    private String message;
    private String username;
    private List<String> symbols;
    private Map<String, Map<String, BigDecimal>> prices;

    public OutputWatchlist() {}

    public OutputWatchlist(String message) {
        this.message = message;
    }

    public OutputWatchlist(String message, String username, List<String> symbols) {
        this.message = message;
        this.username = username;
        this.symbols = symbols;
    }

    public OutputWatchlist(String message, String username, List<String> symbols, Map<String, Map<String, BigDecimal>> prices) {
        this.message = message;
        this.username = username;
        this.symbols = symbols;
        this.prices = prices;
    }

    public String getMessage() { return message; }
    public String getUsername() { return username; }
    public List<String> getSymbols() { return symbols; }
    public Map<String, Map<String, BigDecimal>> getPrices() { return prices; }
}
