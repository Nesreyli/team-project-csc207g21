package use_case.portfolio;

import entity.Portfolio;
import entity.Price;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PortfolioOutputDataFactory {
    /**
     * Creates a PortfolioOutputData object based on the provided portfolio.
     * @param portfolio the Portfolio entity containing the user's portfolio data
     * @return a populated PortfolioOutputData representing the user's portfolio
     */
    public static PortfolioOutputData create(Portfolio portfolio, Map<String, Price> prices) {
        Map<String, BigDecimal> price = new HashMap<>();
        Map<String, BigDecimal> performance = new HashMap<>();
        for(String key : prices.keySet()) {
            performance.put(key, prices.get(key).getYtdPerformance());
            price.put(key, prices.get(key).getPrice());
        }
        return new PortfolioOutputData(portfolio.getUser().getName(),
                portfolio.getUser().getPassword(), portfolio.getCash(), portfolio.getHoldings(),
                portfolio.getValue(), portfolio.getPerformance(), price, performance);
    }
}
