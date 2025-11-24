package UseCase.Stock_Search;

import Entity.Stock_Search;

import java.util.HashMap;
import java.util.List;

public class SearchOutputData {
    private final HashMap<String, Stock_Search> stocks;
    private final String query;
    private final boolean isSuccess;

    public SearchOutputData(HashMap<String, Stock_Search> stocks, String query, boolean isSuccess) {
        this.stocks = stocks;
        this.query = query;
        this.isSuccess = isSuccess;
    }

    public HashMap<String, Stock_Search> getStocks() { return stocks; }

    public String getQuery() { return query; }

    public boolean isSuccess() { return isSuccess; }
}

