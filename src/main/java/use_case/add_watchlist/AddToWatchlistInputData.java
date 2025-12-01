package use_case.add_watchlist;

/**
 * The Input Data for the Add to Watchlist Use Case.
 */

public class AddToWatchlistInputData {
    private final String username;
    private final String password;
    private final String symbol;

    public AddToWatchlistInputData(String username, String password, String symbol) {
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
