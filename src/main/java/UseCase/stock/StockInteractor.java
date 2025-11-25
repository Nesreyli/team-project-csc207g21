package UseCase.stock;

import Entity.Portfolio;
import Entity.Response;
import Entity.Stock;
import Entity.StockSearch;
import UseCase.portfolio.PortfolioAccessInterface;
import UseCase.portfolio.PortfolioOutputData;
import UseCase.portfolio.PortfolioOutputDataFactory;

public class StockInteractor implements StockInputBoundary {
    private final StockAccessInterface stockDB;
    private final PortfolioAccessInterface portDB;
    private final StockOutputBoundary stockOB;

    public StockInteractor(StockAccessInterface stockDB, StockOutputBoundary stockOB,
                           PortfolioAccessInterface portDB) {
        this.stockDB = stockDB;
        this.stockOB = stockOB;
        this.portDB = portDB;
    }

    public void execute(StockInputData input) {
        Response stockResponse = stockDB.getStockData(input.getSymbol());
        Stock stock = (Stock) stockResponse.getEntity();

        Response portResponse = portDB.getPort(input.getUsername(), input.getPassword());
        Portfolio portfolio = (Portfolio) portResponse.getEntity();

        switch(stockResponse.getStatus_code()){
            case 200:
                StockOutputData stockOutputData =
                        StockOutputDataFactory.create(stock);
                PortfolioOutputData portfolioOutputData =
                        PortfolioOutputDataFactory.create(portfolio);
                stockOB.prepareSuccessView(stockOutputData, portfolioOutputData);
                break;
            case 400:
                stockOB.prepareFailView("Incorrect username or password.");
                break;
            case 500:
                stockOB.prepareFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }
}
