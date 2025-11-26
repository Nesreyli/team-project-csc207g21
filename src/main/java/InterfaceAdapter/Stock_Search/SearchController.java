package InterfaceAdapter.Stock_Search;

import UseCase.Stock_Search.SearchInputBoundary;
import UseCase.Stock_Search.SearchInputData;
import UseCase.homebutton.HomeInputBoundary;

public class SearchController {
    private final SearchInputBoundary searchInputBoundary;
    private final HomeInputBoundary homeInputBoundary;

    /**
     * Controller for the Stock Search Use Case.
     */
    public SearchController(SearchInputBoundary searchInputBoundary,
                            HomeInputBoundary homeInputBoundary) {
        this.searchInputBoundary = searchInputBoundary;
        this.homeInputBoundary = homeInputBoundary;
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

    public void goBack(String username, String password){
        homeInputBoundary.executePrevious(username, password);
    }

}
