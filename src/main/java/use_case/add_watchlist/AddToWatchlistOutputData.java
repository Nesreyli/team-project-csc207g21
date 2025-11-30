package use_case.add_watchlist;

import java.util.List;

public class AddToWatchlistOutputData {
    private String message;
    private String symbol;
    private List<String> symbols;

    public AddToWatchlistOutputData(String message, String symbol, List<String> symbols) {
        this.message = message;
        this.symbol = symbol;
        this.symbols = symbols;
    }

    public String getMessage() { return message; }
    public String getSymbol() { return symbol; }
    public List<String> getUpdatedSymbols() { return symbols; }
}
