package InterfaceAdapter.stock_price;

import UseCase.stock_price.PriceInputBoundary;

public class PriceController {
    PriceInputBoundary priceInputBoundary;

    public PriceController(PriceInputBoundary priceInputBoundary){
        this.priceInputBoundary = priceInputBoundary;
    }

    public void executePrice(String symbol){
        priceInputBoundary.execute(symbol);
    }
}
