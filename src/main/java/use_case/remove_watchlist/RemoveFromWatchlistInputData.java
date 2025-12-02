package use_case.remove_watchlist;

/**
 * The Input Data for the Remove From Watchlist Use Case.
 */

public class RemoveFromWatchlistInputData {
    private final String username;
    private final String password;
    private final String symbol;

    /**
     * Constructs RemoveFromWatchlistInputData.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param symbol   the stock symbol to remove
     */
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
