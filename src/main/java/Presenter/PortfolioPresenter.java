package Presenter;

import Application.UseCases.Portfolio.OutputPortfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Singleton
public class PortfolioPresenter {
    public String retrieveHoldings (OutputPortfolio op) {
        Map<String,Integer> holdings = op.getHoldings();
        StringBuilder holdingsString = new StringBuilder();

        for (Map.Entry<String,Integer> holding : holdings.entrySet()) {
            holdingsString.append(holding.getKey()).append(" (").append(holding.getValue()).append(" shares) \n");
        }

        return holdingsString.toString();
    }

    public String retrieveValue(OutputPortfolio op) {
        BigDecimal roundedVal = op.getValue().setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal newVal = new BigDecimal(String.valueOf(roundedVal)).multiply(new BigDecimal(100000));
        return newVal.toString();
    }
}