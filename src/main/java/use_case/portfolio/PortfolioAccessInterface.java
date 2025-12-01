package use_case.portfolio;

public interface PortfolioAccessInterface {
    /**
     * Retrieves a user's portfolio based on the provided credentials.
     * @param username the username of the user
     * @param password the password of the user
     * @return a Response object containing the portfolio data or
     *         information about why the retrieval failed
     */
    entity.Response getPort(String username, String password);
}
