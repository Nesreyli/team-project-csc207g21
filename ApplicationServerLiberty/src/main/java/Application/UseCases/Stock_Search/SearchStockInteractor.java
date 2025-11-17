package Application.UseCases.Stock_Search;

import Application.Entities.Stock;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import java.util.List;
import java.util.ArrayList;


@Singleton
public class SearchStockInteractor {
    @Inject
    SearchStockDatabaseInterface stockDB;

    private List<Stock> cachedStocks = new ArrayList<>();

    public OutputDataSearch executeSearch(SearchStockInput searchStockInput) {
        try{
            String query = searchStockInput.getQuery();

            if (query == null || query.trim().isEmpty()) {
                return new OutputDataSearch("400", new ArrayList<>());
            }

            if (query.length() < 2) {
                return new OutputDataSearch("400", new ArrayList<>());
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

            return new OutputDataSearch("200", results);
        }
        catch (RuntimeException e){
            return new OutputDataSearch("500", new ArrayList<>());
        }
    }

    public OutputDataSearch executeLoadAll() {
        try{
            if (cachedStocks.isEmpty()) {
                cachedStocks = stockDB.getAllStocks();
            }
            return new OutputDataSearch("200", cachedStocks);
        } catch (RuntimeException e){
            return new OutputDataSearch("500", new ArrayList<>());
        }
    }

    private boolean matchesQuery(Stock stock, String query) {
        return stock.getName().toLowerCase().contains(query) ||
                stock.getSymbol().toLowerCase().contains(query);
    }
}
