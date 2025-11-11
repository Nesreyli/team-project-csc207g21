package Application.Entities;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@RequestScoped
public class Portfolio {
    private Long cash;
    private Map<String, Integer> holdings;
    private Long value;

    public Portfolio(){};

    public Portfolio(long c, Map<String, Integer> h, long v){
        cash = c;
        holdings = h;
        value = v;
    }

    public Long getCash() {
        return cash;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public Long getValue() {
        return value;
    }
}
