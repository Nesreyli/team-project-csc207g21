package InterfaceAdapter.portfolio;

import Application.UseCases.Portfolio.OutputPortfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class PortfolioPresenter {
    public PortfolioViewModel portfolioViewModel;

    public PortfolioPresenter() {
        this.portfolioViewModel = new PortfolioViewModel();
    }

    public String retrieveHoldings (OutputPortfolio op) {
        Map<String,Integer> holdings = op.getHoldings();
        StringBuilder holdingsString = new StringBuilder();

        for (Map.Entry<String,Integer> holding : holdings.entrySet()) {
            holdingsString.append(holding.getKey()).append(" (").append(holding.getValue()).append(" shares) \n");
        }

        return holdingsString.toString();
    }

    public Double retrieveValue(OutputPortfolio op) {
        BigDecimal roundedVal = op.getValue().setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal newVal = new BigDecimal(String.valueOf(roundedVal)).multiply(new BigDecimal(100000));
        return newVal.doubleValue();
    }

    public void setBalance(OutputPortfolio op) {
        Double balance = retrieveValue(op);
        portfolioViewModel.setPortfolioState(balance);
    }
}