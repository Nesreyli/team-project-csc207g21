package Application.UseCases.watchlist;

public class WatchlistEntry {
    private String symbol;

    public WatchlistEntry(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
