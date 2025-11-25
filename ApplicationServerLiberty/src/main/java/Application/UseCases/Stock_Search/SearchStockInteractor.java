package main.java.Application.UseCases.Stock_Search;

import Application.Entities.Stock;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import java.util.List;
import java.util.ArrayList;


@Singleton
public class SearchStockInteractor {
    @Inject
    Application.UseCases.Stock_Search.SearchStockDatabaseInterface stockDB;

    private List<Stock> cachedStocks = new ArrayList<>();

    public Application.UseCases.Stock_Search.OutputDataSearch executeSearch(Application.UseCases.Stock_Search.SearchStockInput searchStockInput) {
        try{
            String query = searchStockInput.getQuery();

            if (query == null || query.trim().isEmpty()) {
                return new Application.UseCases.Stock_Search.OutputDataSearch("400", new ArrayList<>());
            }

            if (query.length() < 2) {
                return new Application.UseCases.Stock_Search.OutputDataSearch("400", new ArrayList<>());
            }

            // Loading stocks if not cached
            if (cachedStocks.isEmpty()) {
                cachedStocks = stockDB.getAllStocks();
            }

            // Search through stocks
            String lowerQuery = query.toLowerCase();
            List<Stock> results = cachedStocks.stream()
                    .filter(stock -> matchesQuery(stock, lowerQuery))
                    .toList();

            return new Application.UseCases.Stock_Search.OutputDataSearch("200", results);
        }
        catch (RuntimeException e){
            return new Application.UseCases.Stock_Search.OutputDataSearch("500", new ArrayList<>());
        }
    }

    public Application.UseCases.Stock_Search.OutputDataSearch executeLoadAll() {
        try{
            if (cachedStocks.isEmpty()) {
                cachedStocks = stockDB.getAllStocks();
            }
            return new Application.UseCases.Stock_Search.OutputDataSearch("200", cachedStocks);
        } catch (RuntimeException e){
            return new Application.UseCases.Stock_Search.OutputDataSearch("500", new ArrayList<>());
        }
    }

    private boolean matchesQuery(Stock stock, String query) {
        return stock.getCompany().toLowerCase().contains(query) ||
                stock.getSymbol().toLowerCase().contains(query);
    }
}
