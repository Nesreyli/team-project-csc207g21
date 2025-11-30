package interface_adapter.stock_price;

import interface_adapter.ViewModel;

public class PriceViewModel extends ViewModel<PriceState> {

    public static final String TITLE_LABEL = "Stock Search";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String LOAD_ALL_BUTTON_LABEL = "Load All";
    public static final String SEARCH_FIELD_LABEL = "Enter stock symbol or company name";

    public PriceViewModel() {
        super("Stock Price");
        setState(new PriceState());
    }
}
