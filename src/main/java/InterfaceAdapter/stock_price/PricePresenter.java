package InterfaceAdapter.stock_price;

import Entity.Price;
import InterfaceAdapter.Stock_Search.SearchState;
import InterfaceAdapter.Stock_Search.SearchViewModel;
import InterfaceAdapter.ViewManagerModel;
import UseCase.stock_price.PriceAccessInterface;
import UseCase.stock_price.PriceInputBoundary;
import UseCase.stock_price.PriceOutputBoundary;
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
        priceState.setPrice(price.getPrice().toString());
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
