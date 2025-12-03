package use_case.remove_watchlist;

import java.util.List;

/**
 * Represents the output data for the Remove From Watchlist Use Case.
 * Contains the removed symbol, a message, and the updated list of symbols.
 */

public class RemoveFromWatchlistOutputData {
    private final String symbol;
    private final String message;
    private final List<String> updatedSymbols;

    /**
     * Constructs RemoveFromWatchlistOutputData.
     *
     * @param message        A message indicating success
     * @param symbol         The symbol that was removed
     * @param updatedSymbols The updated list of symbols in the watchlist
     */
    public RemoveFromWatchlistOutputData(String message, String symbol, List<String> updatedSymbols) {
        this.message = message;
        this.symbol = symbol;
        this.updatedSymbols = updatedSymbols;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getUpdatedSymbols() {
        return updatedSymbols;
    }
}
