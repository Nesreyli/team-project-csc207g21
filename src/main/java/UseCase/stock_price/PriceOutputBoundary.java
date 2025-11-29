package UseCase.stock_price;

import Entity.Price;

public interface PriceOutputBoundary {
    public void prepareSuccessView(Price price);

    public void prepareFailView(String message);
}
