package use_case.add_watchlist;

/**
 * The Input Data for the Add to Watchlist Use Case.
 */

public class AddToWatchlistInputData {
    private final String username;
    private final String password;
    private final String symbol;

    /**
     * Constructs AddToWatchlistInputData.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param symbol   the stock symbol to add
     */
    public AddToWatchlistInputData(String username, String password, String symbol) {
        this.username = username;
        this.password = password;
        this.symbol = symbol;
    }

    /**
     * Getter.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter.
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
