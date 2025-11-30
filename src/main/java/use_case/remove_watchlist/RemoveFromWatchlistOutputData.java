package use_case.remove_watchlist;

import java.util.List;

public class RemoveFromWatchlistOutputData {
    private final String symbol;
    private final String message;
    private final List<String> updatedSymbols;

    public RemoveFromWatchlistOutputData(String message, String symbol, List<String> updatedSymbols) {
        this.message = message;
        this.symbol = symbol;
        this.updatedSymbols = updatedSymbols;
    }

    public String getSymbol() { return symbol; }
    public String getMessage() { return message; }
    public List<String> getUpdatedSymbols() { return updatedSymbols; }
}
