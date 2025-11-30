package application.use_case.Price;

import application.entities.Price;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class OutputDataPrice {
    private String message;
    private Map<String, Map<String, BigDecimal>> prices;

    public OutputDataPrice(){
    }

    public OutputDataPrice(String m){
        message = m;
        prices = null;
    }

    //making for each asynchronous
    public OutputDataPrice(String code, ArrayList<Price> p, ArrayList<Price> op){
        message = code;
        prices = new HashMap<>();
        for(int i = 0; i < p.size(); i++ ){
            assert(p.get(i).getSymbol().equals(op.get(i).getSymbol()));
            Map<String, BigDecimal> bothPrice = new HashMap<>();
            BigDecimal current = p.get(i).getPrice();
            BigDecimal open = op.get(i).getPrice();
            bothPrice.put("p", current);
            bothPrice.put("ytd", open); // opening price
            bothPrice.put("perf", current.subtract(open).
                    divide(open.divide(new BigDecimal(100)), 4, RoundingMode.HALF_EVEN));

            prices.put(p.get(i).getSymbol(), bothPrice);
        }
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Map<String, BigDecimal>> getPrices(){
        return prices;
    }
}
