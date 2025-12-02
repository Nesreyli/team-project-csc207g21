package use_case.stock_search;

import java.util.Map;

import entity.Stock_Search;

public class SearchInteractor implements SearchInputBoundary {
    private final StockSearchAccessInterface searchAccessInterface;
    private final SearchOutputBoundary searchOutputBoundary;

    public SearchInteractor(StockSearchAccessInterface searchAccessInterface, SearchOutputBoundary searchOutputBoundary) {
        this.searchAccessInterface = searchAccessInterface;
        this.searchOutputBoundary = searchOutputBoundary;
    }

    @Override
    public void executeSearch(SearchInputData input) {
        final String query = input.getQuery();

        if (query == null || query.isEmpty()) {
            searchOutputBoundary.prepareFailView("Search Query cannot be Empty");
        }

        try {
            final Map<String, Stock_Search> results = searchAccessInterface.searchStocks(query);

            final SearchOutputData outputData = new SearchOutputData(results, query, true);
            searchOutputBoundary.prepareSuccessView(outputData);
        }
        catch (Exception error) {
            searchOutputBoundary.prepareFailView("Please enter a valid company name or symbol");
        }
    }

    @Override
    public void executeLoadAll() {
        try {
            final Map<String, Stock_Search> allStocks = searchAccessInterface.getAllStocks();
            final SearchOutputData outputData = new SearchOutputData(allStocks, "", true);
            searchOutputBoundary.prepareSuccessView(outputData);
        }
        catch (Exception error) {
            searchOutputBoundary.prepareFailView("Failure to load all stocks: " + error.getMessage());
        }
    }

}