package UseCase.portfolio;

import Entity.Portfolio;

public class PortfolioOutputDataFactory {
    public static PortfolioOutputData create(Portfolio p){
        return new PortfolioOutputData(p.getUser().getName(),
                p.getUser().getPassword(), p.getCash(), p.getHoldings(),
                p.getValue(), p.getPerformance());
    }
}
