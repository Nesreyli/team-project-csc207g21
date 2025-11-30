package use_case.stock_price;

import entity.Price;

public interface PriceOutputBoundary {
    public void prepareSuccessView(Price price);

    public void prepareFailView(String message);
}
