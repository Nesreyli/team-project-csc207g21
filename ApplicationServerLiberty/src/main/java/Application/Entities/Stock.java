package Application.Entities;

import jakarta.enterprise.context.RequestScoped;
import java.math.BigDecimal;

@RequestScoped
public class Stock {
    private String symbol;
    private String company;
    private BigDecimal price;
    private String country;

    public Stock() {}

    public Stock(String symbol, String company, BigDecimal price, String country) {
        this.symbol = symbol;
        this.company = company;
        this.price = price;
        this.country = country;
    }

    public String getSymbol() {return symbol;}

    public String getCompany() {return company;}

    public BigDecimal getPrice() { return price; }

    public String getCountry() { return country; }
}


