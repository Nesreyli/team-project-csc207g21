package interface_adapter.Stock_Search;

import interface_adapter.ViewModel;

public class SearchViewModel extends ViewModel<SearchState> {

    public static final String TITLE_LABEL = "Stock Search";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String LOAD_ALL_BUTTON_LABEL = "Load All";
    public static final String SEARCH_FIELD_LABEL = "Enter stock symbol or company name";

    public SearchViewModel() {
        super("Stock Search");
        setState(new SearchState());
    }
}
