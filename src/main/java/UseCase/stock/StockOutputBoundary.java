package UseCase.stock;

import UseCase.portfolio.PortfolioOutputData;

public interface StockOutputBoundary {
    void prepareSuccessView(StockOutputData stockOD, PortfolioOutputData portOD);
    void prepareFailView(String message);
}
