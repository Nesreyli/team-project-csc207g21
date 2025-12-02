package use_case.add_watchlist;

import java.util.List;

public class AddToWatchlistOutputData {
    private final String message;
    private final String symbol;
    private final List<String> symbols;

    public AddToWatchlistOutputData(String message, String symbol, List<String> symbols) {
        this.message = message;
        this.symbol = symbol;
        this.symbols = symbols;
    }

    /**
     * Getter.
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter.
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter.
     * @return updated symbols
     */
    public List<String> getUpdatedSymbols() {
        return symbols;
    }
}
