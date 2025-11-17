package Application.UseCases.Stock_Search;

import Application.Entities.Stock;

import java.util.List;

public interface SearchStockDatabaseInterface {
    List<Stock> getAllStocks();
    List<Stock> searchStock(String query);
}
