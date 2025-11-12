package Application.Entities;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;

@RequestScoped
public class Portfolio {
    private Long cash;
    private Map<String, Integer> holdings;
    private Long value;
    private BigDecimal performance;

    public Portfolio(){};

    public Portfolio(long c, Map<String, Integer> h, long v, BigDecimal p){
        cash = c;
        holdings = h;
        value = v;
        performance = p;
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

    public BigDecimal getPerformance() {
        return performance;
    }
}
