package UseCase.portfolio;

/**
 * The Input Data for the Login Use Case.
 */
public class PortfolioInputData {

    private final String username;
    private final String password;

    public PortfolioInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
