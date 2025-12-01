package use_case.watchlist;

/**
 * The Input Data for the Watchlist Use Case.
 */

public class WatchlistInputData {
    private final String username;
    private final String password;

    public WatchlistInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
