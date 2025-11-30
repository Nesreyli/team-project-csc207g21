package application.use_case.Stock_Search;

import application.entities.Stock;

import java.util.List;

public interface SearchStockDatabaseInterface {
    List<Stock> getAllStocks();
    List<Stock> searchStock(String query);
}
