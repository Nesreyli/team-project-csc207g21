package interface_adapter.stock_price;

import entity.Price;
import interface_adapter.Stock_Search.SearchState;
import interface_adapter.Stock_Search.SearchViewModel;
import interface_adapter.ViewManagerModel;
import use_case.stock_price.PriceOutputBoundary;
import kotlin.NotImplementedError;


public class PricePresenter implements PriceOutputBoundary {
    ViewManagerModel viewManagerModel;
    SearchViewModel searchViewModel;
    PriceViewModel priceViewModel;

    public PricePresenter(ViewManagerModel viewManagerModel,
                          SearchViewModel searchViewModel,
                          PriceViewModel priceViewModel){
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.priceViewModel = priceViewModel;
    }

    @Override
    public void prepareSuccessView(Price price) {
        SearchState searchState = searchViewModel.getState();
        PriceState priceState = priceViewModel.getState();
        priceState.setUsername(searchState.getUsername());
        priceState.setPassword(searchState.getPassword());
        priceState.setCompany(searchState.getSearchResults().
                get(price.getSymbol()).getCompany());
        priceState.setCountry(searchState.getSearchResults().
                get(price.getSymbol()).getCountry());
        priceState.setPrice(price.getPrice());
        priceState.setYtdPerformance(price.getYtdPerformance().toString());
        priceState.setYtdPrice(price.getYtdPrice().toString());
        priceState.setSymbol(price.getSymbol());

        priceViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        throw new NotImplementedError();
    }
}
