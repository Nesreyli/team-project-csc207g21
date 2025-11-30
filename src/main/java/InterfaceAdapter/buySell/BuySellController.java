package InterfaceAdapter.buySell;

import InterfaceAdapter.stock_price.PriceState;
import UseCase.buySell.BuySellInputBoundary;
import UseCase.buySell.BuySellInputData;

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