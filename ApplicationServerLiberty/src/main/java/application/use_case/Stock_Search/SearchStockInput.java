package application.use_case.Stock_Search;

public class SearchStockInput {
    private String query;

    public SearchStockInput(){}

    public SearchStockInput(String query){
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}

