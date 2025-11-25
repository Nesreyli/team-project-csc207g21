package UseCase.portfolio;

public class PortfolioInputData {
    private final String username;
    private final String password;

    public PortfolioInputData(String username, String password) {
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
