package UseCase.buySell;

import Entity.BuySellReceipt;
import Entity.Portfolio;
import Entity.Response;
import Entity.Stock;
import UseCase.portfolio.PortfolioAccessInterface;
import UseCase.portfolio.PortfolioOutputData;
import UseCase.portfolio.PortfolioOutputDataFactory;

public class BuySellInteractor implements BuySellInputBoundary {
    private final BuySellAccessInterface buySellDB;
    private final BuySellOutputBoundary buySellOB;

    public BuySellInteractor(BuySellAccessInterface buySellDB, BuySellOutputBoundary buySellOB) {
        this.buySellDB = buySellDB;
        this.buySellOB = buySellOB;
    }

    public void execute(BuySellInputData input) {
        Response buySellResponse = buySellDB.setStockData(input.getPriceState(), input.getAmount(), input.getIsBuy());
        BuySellReceipt receipt = (BuySellReceipt) buySellResponse.getEntity();

        switch(buySellResponse.getStatus_code()){
            case 200:
                BuySellOutputData buySellOutputData =
                        BuySellOutputDataFactory.create(receipt);
                buySellOB.prepareSuccessView(buySellOutputData);
                break;
            case 400:
                buySellOB.prepareFailView("Inadequate balance/shares");
                break;
            case 500:
                buySellOB.prepareFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }
}
