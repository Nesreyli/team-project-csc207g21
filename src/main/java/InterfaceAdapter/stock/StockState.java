package InterfaceAdapter.stock;

import java.math.BigDecimal;

public class StockState {
    private String username;
    private String password;
    private String symbol;
    private BigDecimal price;
    private BigDecimal ytdprice;
    private BigDecimal performance;
    private String country;
    private String company;
    private BigDecimal value;
    private Integer sharesOwned;

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getYtdprice() { return ytdprice; }

    public void setYtdprice(BigDecimal ytd) { this.ytdprice = ytd; }

    public String getSymbol() { return symbol; }

    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getPerformance() { return performance; }

    public void setPerformance(BigDecimal performance) { this.performance = performance; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    public BigDecimal getValue() { return value; }

    public void setValue(BigDecimal value) { this.value = value; }

    public Integer getSharesOwned() { return sharesOwned; }

    public void setSharesOwned(Integer sharesOwned) { this.sharesOwned = sharesOwned; }
}
