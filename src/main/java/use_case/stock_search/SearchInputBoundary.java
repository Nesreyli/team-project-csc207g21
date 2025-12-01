package use_case.stock_search;

public interface SearchInputBoundary {

    /**
     * Executes a search for a specific stock using the provided input data.
     * @param inputData the data containing search parameters, such as stock symbol
     */
    void executeSearch(SearchInputData inputData);

    /**
     * Loads all available stocks.
     * Implementations should retrieve all stock data and pass the results
     * to the output boundary for presentation.
     */
    void executeLoadAll();
}
