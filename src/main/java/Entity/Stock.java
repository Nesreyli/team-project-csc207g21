package Entity;

import java.math.BigDecimal;

public class Stock {
    private final String symbol;
    private final String company;
    private final BigDecimal price;
    private final String country;


    public Stock(String symbol, String company, BigDecimal price, String country) {
        this.symbol = symbol;
        this.company = company;
        this.price = price;
        this.country = country;
    }

    public String getSymbol() { return symbol; }

    public String getCompany() { return company; }

    public BigDecimal getPrice() { return price; }

    public String getCountry() { return country; }

    @Override
    public String toString() {
        return String.format("%s (%s) -$%.2f", company, symbol, price);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stock stock = (Stock) obj;
        return symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
