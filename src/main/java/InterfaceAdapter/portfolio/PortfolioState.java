package InterfaceAdapter.portfolio;

import java.util.Map;

/**
 * The State information representing the logged-in user.
 */
public class PortfolioState {
    private String username = "";

    private String password = "";

    private String value = "";

    private String cash = "";

    private String performance = "";

    private Map<String, Object> holdings;

//    public PortfolioState(PortfolioState copy) {
//        username = copy.username;
//        password = copy.password;
//        value = copy.value;
//        performance = copy.performance;
//        cash = copy.cash;
//    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public PortfolioState() {
    }

    public String getUsername() {
        return username;
    }

    public String getCash() {
        return cash;
    }

    public String getPerformance() {
        return performance;
    }

    public String getValue() {
        return value;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Object> holdings) {
        this.holdings = holdings;
    }

    public String getPassword() {
        return password;
    }
}