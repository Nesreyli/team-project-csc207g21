package Application.UseCases.Price;

import Application.Entities.Price;

import java.util.ArrayList;

public interface StockDatabaseInterface {
    Price checkOrder(PricesInput symbols);

    ArrayList<Price> checkPrice(PricesInput symbols) throws RuntimeException;

    public ArrayList<Price> checkOpen(PricesInput symbols);
}
