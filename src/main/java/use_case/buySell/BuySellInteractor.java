package use_case.buySell;

import entity.BuySellReceipt;
import entity.Response;

public class BuySellInteractor implements BuySellInputBoundary {
    private final BuySellAccessInterface buySellDb;
    private final BuySellOutputBoundary buySellOb;

    public BuySellInteractor(BuySellAccessInterface buySellDb, BuySellOutputBoundary buySellOb) {
        this.buySellDb = buySellDb;
        this.buySellOb = buySellOb;
    }

    /**
     * Executes the Buy/Sell use case using the provided input data.
     * The interactor sends the request to the data access layer, interprets the
     * Response status code, and routes control to the appropriate
     * output boundary method.
     *
     * @param input the data representing the buy or sell request
     */
    public void execute(BuySellInputData input) {
        final Response buySellResponse = buySellDb.setStockData(input.getPriceState(),
                input.getAmount(), input.getIsBuy());
        final BuySellReceipt receipt = (BuySellReceipt) buySellResponse.getEntity();

        switch (buySellResponse.getStatus_code()) {
            case 200:
                final BuySellOutputData buySellOutputData =
                        BuySellOutputDataFactory.create(receipt);
                buySellOb.prepareSuccessView(buySellOutputData);
                break;
            case 400:
                buySellOb.prepareFailView("You don't have enough funds/shares!");
                break;
            case 500:
                buySellOb.prepareFailView("Server Error");
                break;
            default:
                throw new RuntimeException("Unhandled status code in BuySellInteractor");
        }
    }
}
