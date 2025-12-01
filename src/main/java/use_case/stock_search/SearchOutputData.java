package use_case.stock_search;

import java.util.Map;

import entity.Stock_Search;

public class SearchOutputData {
    private final Map<String, Stock_Search> stocks;
    private final String query;
    private final boolean isSuccess;

    public SearchOutputData(Map<String, Stock_Search> stocks, String query, boolean isSuccess) {
        this.stocks = stocks;
        this.query = query;
        this.isSuccess = isSuccess;
    }

    public Map<String, Stock_Search> getStocks() {
        return stocks;
    }

    public String getQuery() {
        return query;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}

