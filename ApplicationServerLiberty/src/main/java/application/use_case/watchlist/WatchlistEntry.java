package application.use_case.watchlist;

public class WatchlistEntry {
    private String symbol;

    public WatchlistEntry() {

    }

    public WatchlistEntry(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
