package UseCase.stock_price;

import Entity.Response;

public interface PriceAccessInterface {
    public Response getPrice(String symbol);
}
