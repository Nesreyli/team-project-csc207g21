package application.use_case.Portfolio;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@RequestScoped
public class OutputPortfolio {
    private String message;
    private String username;
    private BigDecimal cash;
    private Map<String, Integer> holdings;
    private BigDecimal value;
    private BigDecimal performance;

    public OutputPortfolio(){};

    public OutputPortfolio(String m){
        message = m;
    }

    public OutputPortfolio(String m, String u, long c, Map<String, Integer> h, long v, BigDecimal p){
        message = m;
        username = u;
        // okay to assume rounding mode unnecessary as it depends on entity storing 100000 as 1 dollar in long int.
        cash = new BigDecimal(c).divide(new BigDecimal(100000), 5, RoundingMode.UNNECESSARY);
        holdings = h;
        value = new BigDecimal(v).divide(new BigDecimal(100000), 5, RoundingMode.UNNECESSARY);
        performance = p;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getPerformance() {
        return performance;
    }
}
