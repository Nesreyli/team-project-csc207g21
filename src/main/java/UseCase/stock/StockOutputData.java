package UseCase.stock;

import java.math.BigDecimal;

public class StockOutputData {
    private final String username;
    private final String password;
    private final Integer owned;
    private final BigDecimal price;
    private final BigDecimal value;
    private final String company;
    private final String country;

    public StockOutputData(String username, String password, Integer owned, BigDecimal price,
                           BigDecimal value, String company, String country) {
        this.username = username;
        this.password = password;
        this.owned = owned;
        this.price = price;
        this.value = value;
        this.company = company;
        this.country = country;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public Integer getOwned() { return owned; }

    public BigDecimal getPrice() { return price; }

    public BigDecimal getValue() { return value; }

    public String getCompany() { return company; }

    public String getCountry() { return country; }
}
