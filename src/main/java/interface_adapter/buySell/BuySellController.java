package interface_adapter.buySell;

import interface_adapter.stock_price.PriceState;
import use_case.buySell.BuySellInputBoundary;
import use_case.buySell.BuySellInputData;

public class BuySellController {

    private final BuySellInputBoundary BSInteractor;

    public BuySellController(BuySellInputBoundary BSInteractor) {
        this.BSInteractor = BSInteractor;
    }

    /**
     * Converts parameters into an input data object and passes it to the Buy/Sell use case.
     *
     * @param priceState The current state of the interface that displays stock information
     * @param amount The number of stocks to buy or sell
     * @param isBuy Whether to buy or sell; true indicates a purchase, and false indicates a sale.
     */
    public void execute(PriceState priceState, Integer amount, Boolean isBuy) {
        final BuySellInputData BSInputData = new BuySellInputData(priceState, amount, isBuy);
        BSInteractor.execute(BSInputData);
    }
}