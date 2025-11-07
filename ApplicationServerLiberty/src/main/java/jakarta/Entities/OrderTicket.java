package jakarta.Entities;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;

@RequestScoped
public class OrderTicket {
    // use enum later
    private char order;
    private String symbol;
    private int amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OrderTicket(){}
    public OrderTicket(char o, String s, int a, BigDecimal p, BigDecimal tp){
        order = o;
        symbol = s;
        amount = a;
        price = p;
        totalPrice = tp;
    }

    public char getOrder() {
        return order;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
