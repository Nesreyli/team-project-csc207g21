package use_case.buySell;

import entity.BuySellReceipt;
import entity.Portfolio;
import entity.Response;
import use_case.portfolio.PortfolioAccessInterface;
import use_case.portfolio.PortfolioOutputData;
import use_case.portfolio.PortfolioOutputDataFactory;

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
