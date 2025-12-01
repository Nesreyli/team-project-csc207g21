package interface_adapter.buySell;

import interface_adapter.stock_price.PriceState;
import use_case.buySell.BuySellInputBoundary;
import use_case.buySell.BuySellInputData;

public class BuySellController {

    private final BuySellInputBoundary BSInteractor;

    public BuySellController(BuySellInputBoundary BSInteractor) {
        this.BSInteractor = BSInteractor;
    }

    public void execute(PriceState priceState, Integer amount, Boolean isBuy) {
        final BuySellInputData BSInputData = new BuySellInputData(priceState, amount, isBuy);
        BSInteractor.execute(BSInputData);
    }
}