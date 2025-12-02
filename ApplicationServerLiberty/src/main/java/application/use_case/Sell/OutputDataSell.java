package application.use_case.Sell;

import java.math.BigDecimal;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class OutputDataSell {
    private String message;
    private Character order;
    private String symbol;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OutputDataSell() {

    }

    public OutputDataSell(String message) {
        this(message, null, null, null, null, null);
    }

    public OutputDataSell(String mess, Character ord, String sym, Integer num,
                         BigDecimal pri, BigDecimal totalpri) {
        message = mess;
        order = ord;
        symbol = sym;
        amount = num;
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

