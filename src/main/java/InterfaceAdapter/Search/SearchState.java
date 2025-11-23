package InterfaceAdapter.Search;

import Entity.Stock;
import java.util.List;
import java.util.ArrayList;

/**
 * The State information for the Stock Search View.
 */
public class SearchState {
    private String searchQuery = "";
    private String searchError;
    private List<Stock> searchResult  = new ArrayList<>();
    private Stock selectedStock;
    private boolean isLoading = false;

    public SearchState(SearchState copy) {
        searchQuery = copy.searchQuery;
        searchError = copy.searchError;
        searchResult = new ArrayList<>(copy.searchResult);
        selectedStock = copy.selectedStock;
        isLoading = copy.isLoading;
    }

    public SearchState() {}

    public String getSearchQuery() { return searchQuery; }

    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public String getSearchError() { return searchError; }

    public void setSearchError(String searchError) { this.searchError = searchError; }

    public List<Stock> getSearchResult() { return searchResult; }

    public void setSearchResult(List<Stock> searchResult) { this.searchResult = searchResult; }

    public Stock getSelectedStock() { return selectedStock; }

    public void setSelectedStock(Stock selectedStock) { this.selectedStock = selectedStock; }

    public boolean isLoading() { return isLoading; }

    public void setLoading(boolean isLoading) { this.isLoading = isLoading; }

}
