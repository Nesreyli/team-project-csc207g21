package use_case.portfolio;

import entity.Portfolio;

public class PortfolioOutputDataFactory {
    /**
     * Creates a PortfolioOutputData object based on the provided portfolio.
     * @param portfolio the Portfolio entity containing the user's portfolio data
     * @return a populated PortfolioOutputData representing the user's portfolio
     */
    public static PortfolioOutputData create(Portfolio portfolio) {
        return new PortfolioOutputData(portfolio.getUser().getName(),
                portfolio.getUser().getPassword(), portfolio.getCash(), portfolio.getHoldings(),
                portfolio.getValue(), portfolio.getPerformance());
    }
}
