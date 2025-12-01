package interface_adapter.Stock_Search;

import use_case.homebutton.HomeInputBoundary;
import use_case.stock_search.SearchInputBoundary;
import use_case.stock_search.SearchInputData;

public class SearchController {
    private final SearchInputBoundary searchInputBoundary;
    private final HomeInputBoundary homeInputBoundary;

    /**
     * Constructs a {@code SearchController} with the required input boundaries.
     *
     * @param searchInputBoundary the use case boundary for searching stocks
     * @param homeInputBoundary   the use case boundary for navigation back to the home view
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
    public void executeSearch(String query) {
        final SearchInputData inputData = new SearchInputData(query);
        searchInputBoundary.executeSearch(inputData);
    }

    /**
     * Executes loading all stocks.
     */
    public void executeLoadAll() {
        searchInputBoundary.executeLoadAll();
    }

    /**
     * Navigates back to the previous screen (typically the home/portfolio view).
     *
     * @param username the logged-in user's username
     * @param password the user's password for session revalidation
     */
    public void goBack(String username, String password) {
        homeInputBoundary.executePrevious(username, password);
    }

}
