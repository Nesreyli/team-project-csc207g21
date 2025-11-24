package InterfaceAdapter.Stock_Search;

import Entity.Stock_Search;

import java.util.HashMap;
import java.util.Map;

/**
 * The State information for the Stock Search View.
 */
public class SearchState {
    private String searchQuery = "";
    private String searchError;
    private Map<String, Stock_Search> searchResults = new HashMap<>();
    private boolean isLoading = false;
    private String username;
    private String password;

    public SearchState(SearchState copy) {
        searchQuery = copy.searchQuery;
        searchError = copy.searchError;
        searchResults = new HashMap<>(copy.searchResults);
        isLoading = copy.isLoading;
        username = copy.username;
        password = copy.password;
    }

    public SearchState() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSearchQuery() { return searchQuery; }

    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public String getSearchError() { return searchError; }

    public void setSearchError(String searchError) { this.searchError = searchError; }

    public Map<String, Stock_Search> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(Map<String, Stock_Search> searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isLoading() { return isLoading; }

    public void setLoading(boolean isLoading) { this.isLoading = isLoading; }

}
