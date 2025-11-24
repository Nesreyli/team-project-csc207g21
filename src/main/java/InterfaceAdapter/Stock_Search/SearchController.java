package InterfaceAdapter.Stock_Search;

import UseCase.Stock_Search.SearchInputBoundary;
import UseCase.Stock_Search.SearchInputData;

public class SearchController {
    private final SearchInputBoundary searchInputBoundary;

    /**
     * Controller for the Stock Search Use Case.
     */
    public SearchController(SearchInputBoundary searchInputBoundary) {
        this.searchInputBoundary = searchInputBoundary;
    }

    /**
     * Executes the stock search use case.
     * @param query the search query
     */

    public void executeSearch(String query){
        final SearchInputData inputData = new SearchInputData(query);
        searchInputBoundary.executeSearch(inputData);
    }

    /**
     * Executes loading all stocks.
     */
    public void executeLoadAll(){
        searchInputBoundary.executeLoadAll();
    }

}
