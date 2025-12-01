package use_case.remove_watchlist;

public class RemoveFromWatchlistInputData {
    private final String username;
    private final String password;
    private final String symbol;

    public RemoveFromWatchlistInputData(String username, String password, String symbol) {
        this.username = username;
        this.password = password;
        this.symbol = symbol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSymbol() {
        return symbol;
    }
}
