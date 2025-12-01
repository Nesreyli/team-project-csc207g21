package use_case.add_watchlist;

import java.util.List;

/**
 * The Output Data for the Add to Watchlist Use Case.
 */

public class AddToWatchlistOutputData {
    private final String message;
    private final String symbol;
    private final List<String> symbols;

    public AddToWatchlistOutputData(String message, String symbol, List<String> symbols) {
        this.message = message;
        this.symbol = symbol;
        this.symbols = symbols;
    }

    public String getMessage() {
        return message;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<String> getUpdatedSymbols() {
        return symbols;
    }
}
