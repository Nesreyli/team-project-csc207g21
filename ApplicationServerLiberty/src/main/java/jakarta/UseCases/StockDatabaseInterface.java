package jakarta.UseCases;

import jakarta.Entities.Price;

public interface StockDatabaseInterface {
    Price checkOrder(String symbol);

    Price checkPrice(String symbol) throws RuntimeException;
}
