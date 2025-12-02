package use_case.stock_search;

import java.util.Map;

import data_access.SearchAccessObject;
import entity.Stock_Search;

public class SearchInteractor implements SearchInputBoundary {
    private final SearchAccessObject searchAccessObject;
    private final SearchOutputBoundary searchOutputBoundary;

    public SearchInteractor(SearchAccessObject searchAccessObject, SearchOutputBoundary searchOutputBoundary) {
        this.searchAccessObject = searchAccessObject;
        this.searchOutputBoundary = searchOutputBoundary;
    }

    @Override
    public void executeSearch(SearchInputData input) {
        final String query = input.getQuery();
        try {
            final Map<String, Stock_Search> results = searchAccessObject.searchStocks(query);

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
            final Map<String, Stock_Search> allStocks = searchAccessObject.getAllStocks();
            final SearchOutputData outputData = new SearchOutputData(allStocks, "", true);
            searchOutputBoundary.prepareSuccessView(outputData);
        }
        catch (Exception error) {
            searchOutputBoundary.prepareFailView("Failure to load all stocks: " + error.getMessage());
        }
    }

}
