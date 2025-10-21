package jakarta.UseCases;

import jakarta.Entities.Price;

import java.util.ArrayList;

public interface StockDatabaseInterface {
    Price checkOrder(PricesInputData symbols);

    ArrayList<Price> checkPrice(PricesInputData symbols) throws RuntimeException;
}
