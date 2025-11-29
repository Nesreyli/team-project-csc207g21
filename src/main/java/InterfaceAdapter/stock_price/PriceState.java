package InterfaceAdapter.stock_price;

import java.math.BigDecimal;

public class PriceState {
    private BigDecimal price;
    private String ytdPrice;
    private String ytdPerformance;
    private String company;
    private String country;
    private String username;
    private String password;
    private String symbol;

    public PriceState(PriceState copy) {
        price = copy.price;
        ytdPrice = copy.ytdPrice;
        ytdPerformance = copy.ytdPerformance;
        company = copy.company;
        country = copy.country;
        username = copy.username;
        password = copy.password;
    }

    public PriceState() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public String getYtdPrice() { return ytdPrice; }

    public void setYtdPrice(String ytdPrice) { this.ytdPrice = ytdPrice; }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getYtdPerformance() {
        return ytdPerformance;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setYtdPerformance(String ytdPerformance) {
        this.ytdPerformance = ytdPerformance;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
