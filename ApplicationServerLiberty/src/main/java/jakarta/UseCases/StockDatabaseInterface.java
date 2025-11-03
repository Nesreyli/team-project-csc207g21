package jakarta.UseCases;

import jakarta.Entities.Price;

import java.util.ArrayList;

public interface StockDatabaseInterface {
    Price checkOrder(PricesInput symbols);

    ArrayList<Price> checkPrice(PricesInput symbols) throws RuntimeException;
}
