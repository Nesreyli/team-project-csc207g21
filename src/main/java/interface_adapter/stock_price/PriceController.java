package interface_adapter.stock_price;

import use_case.stock_price.PriceInputBoundary;

public class PriceController {
    PriceInputBoundary priceInputBoundary;

    public PriceController(PriceInputBoundary priceInputBoundary){
        this.priceInputBoundary = priceInputBoundary;
    }

    public void executePrice(String symbol){
        priceInputBoundary.execute(symbol);
    }
}
