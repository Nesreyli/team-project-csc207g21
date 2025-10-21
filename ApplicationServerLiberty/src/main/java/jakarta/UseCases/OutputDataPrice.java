package jakarta.UseCases;

import jakarta.Entities.Price;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;

@RequestScoped
public class OutputDataPrice {
    private String message;
    private String symbol;
    private BigDecimal price;

    public OutputDataPrice(String code, String s, BigDecimal p){
        message = code;
        symbol = s;
        price = p;
    }

    public String getMessage() {
        return message;
    }

    public String getSymbol(){
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
