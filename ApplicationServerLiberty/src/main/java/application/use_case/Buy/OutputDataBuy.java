package application.use_case.Buy;

import java.math.BigDecimal;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class OutputDataBuy {
    private String message;
    private Character order;
    private String symbol;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OutputDataBuy() {

    }

    public OutputDataBuy(String mess) {
        this(mess, null, null, null, null, null);
    }

    public OutputDataBuy(String mess, Character ord, String sym, Integer amunt,
                         BigDecimal pri, BigDecimal totalpri) {
        message = mess;
        order = ord;
        symbol = sym;
        amount = amunt;
        price = pri;
        totalPrice = totalpri;
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

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}

