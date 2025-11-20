package Entity;

import java.math.BigDecimal;
import java.util.Map;

public class Portfolio {
    private User user;
    private BigDecimal cash;
    private Map<String, Object> holdings;
    private BigDecimal value;
    private BigDecimal performance;

    public Portfolio(){
    }

    public User getUser() {
        return user;
    }
    public BigDecimal getCash() {
        return cash;
    }

    public Map<String, Object> getHoldings() {
        return holdings;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public static class Builder{
        private final Portfolio port;
        public Builder(){
            port = new Portfolio();
        }
        public Builder user(User user){
            port.user = user;
            return this;
        }
        public Builder cash(BigDecimal cash){
            port.cash = cash;
            return this;
        }
        public Builder holdings(Map<String, Object> holdings){
            port.holdings = holdings;
            return this;
        }
        public Builder value(BigDecimal value){
            port.value = value;
            return this;
        }
        public Builder performance(BigDecimal performance){
            port.performance = performance;
            return this;
        }
        public Portfolio build() {
            return port;
        }
    }

}
