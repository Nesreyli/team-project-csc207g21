package Entity;

import java.math.BigDecimal;

public class StockResponse {
    private Integer owned;
    private BigDecimal value;
    private BigDecimal price;
    private String company;
    private String country;

    public StockResponse(Integer owned, BigDecimal value, BigDecimal price, String company, String country) {
        this.owned = owned;
        this.value = value;
        this.price = price;
        this.company = company;
        this.country = country;
    }

    public Integer getOwned() {
        return owned;
    }

    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }
}
