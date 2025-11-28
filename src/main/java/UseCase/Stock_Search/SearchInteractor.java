package UseCase.Stock_Search;

import DataAccess.SearchAccessObject;
import Entity.Stock_Search;

import java.util.HashMap;
import java.util.List;

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

        if (query == null || query.isEmpty()) {
            searchOutputBoundary.prepareFailView("Search Query cannot be Empty");
        }

        try{
            HashMap<String, Stock_Search> results = searchAccessObject.searchStocks(query);

            SearchOutputData outputData = new SearchOutputData(results, query, true);
            searchOutputBoundary.prepareSuccessView(outputData);
        } catch (Exception e){
            searchOutputBoundary.prepareFailView("Failure to search stocks: " + e.getMessage());
        }
    }

    @Override
    public void executeLoadAll(){
        try {
            HashMap<String, Stock_Search> allStocks = searchAccessObject.getAllStocks();
            SearchOutputData outputData = new SearchOutputData(allStocks, "", true);
            searchOutputBoundary.prepareSuccessView(outputData);
        } catch (Exception e){
            searchOutputBoundary.prepareFailView("Failure to load all stocks: " + e.getMessage());
        }
    }

}
