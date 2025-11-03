package jakarta.UseCases;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MarketBuyInput {
    private String symbol;
    private String amount;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
