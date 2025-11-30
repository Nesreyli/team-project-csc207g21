package use_case.stock_price;

import entity.Response;

public interface PriceAccessInterface {
    public Response getPrice(String symbol);
}
