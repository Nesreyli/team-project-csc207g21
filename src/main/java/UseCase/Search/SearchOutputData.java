package UseCase.Search;

import Entity.Stock;
import java.util.List;

public class SearchOutputData {
    private final List<Stock> stocks;
    private final String query;
    private final boolean isSuccess;

    public SearchOutputData(List<Stock> stocks, String query, boolean isSuccess) {
        this.stocks = stocks;
        this.query = query;
        this.isSuccess = isSuccess;
    }

    public List<Stock> getStocks() { return stocks; }

    public String getQuery() { return query; }

    public boolean isSuccess() { return isSuccess; }
}

