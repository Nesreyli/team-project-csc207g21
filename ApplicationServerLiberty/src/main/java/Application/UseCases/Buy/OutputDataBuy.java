package jakarta.UseCases.Buy;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;

@RequestScoped
public class OutputDataBuy {
    private String message;
    private Character order;
    private String symbol;
    private int amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OutputDataBuy(){}
    public OutputDataBuy(String m){
        this(m,null,null, 0,null,null);
    }
    public OutputDataBuy(String m, Character o, String s, int n,
                         BigDecimal p, BigDecimal tp){
        message = m;
        order = o;
        symbol = s;
        amount = n;
        price = p;
        totalPrice = tp;
    }

    public String getMessage() {
        return message;
    }

    public Character getOrder() {
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

