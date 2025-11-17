package Application.Entities;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class Stock {
    private String symbol;
    private String name;
    private double price;
    private String country;

    public Stock() {}

    public Stock(String symbol, String name, double price, String country) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.country = country;
    }

    public String getSymbol() {return symbol;}

    public String getName() {return name;}

    public double getPrice() { return price; }

    public String getCountry() { return country; }
}


