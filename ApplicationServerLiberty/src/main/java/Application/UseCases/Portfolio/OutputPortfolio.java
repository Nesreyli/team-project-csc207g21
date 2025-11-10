package Application.UseCases.Portfolio;

import jakarta.enterprise.context.RequestScoped;

import java.util.Map;

@RequestScoped
public class OutputPortfolio {
    private String message;
    private String username;
    private Long cash;
    private Map<String, Integer> holdings;
    private Long value;

    public OutputPortfolio(){};

    public OutputPortfolio(String m){
        message = m;
    }

    public OutputPortfolio(String m, String u, long c, Map<String, Integer> h, long v){
        message = m;
        username = u;
        cash = c;
        holdings = h;
        value = v;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
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
