package use_case.watchlist;

/**
 * The Input Data for the Watchlist Use Case.
 */

public class WatchlistInputData {
    private final String username;
    private final String password;

    /**
     * Constructs a WatchlistInputData object with the given credentials.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public WatchlistInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
