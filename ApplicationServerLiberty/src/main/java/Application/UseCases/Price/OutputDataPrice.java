package jakarta.UseCases.Price;

import jakarta.Entities.Price;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class OutputDataPrice {
    private String message;
    private Map<String, BigDecimal> prices;

    public OutputDataPrice(){
    }

    public OutputDataPrice(String m){
        message = m;
        prices = null;
    }

    //making for each asynchronous
    public OutputDataPrice(String code, ArrayList<Price> p){
        message = code;
        prices = new HashMap<>();
        for(Price price: p){
            prices.put(price.getSymbol(), price.getPrice());
        }
    }

    public String getMessage() {
        return message;
    }

    public Map<String, BigDecimal> getPrices(){
        return prices;
    }
}
