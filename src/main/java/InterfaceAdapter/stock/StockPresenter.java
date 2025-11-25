package InterfaceAdapter.stock;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import UseCase.portfolio.PortfolioOutputData;
import UseCase.stock.StockOutputBoundary;
import UseCase.stock.StockOutputData;
import InterfaceAdapter.Stock_Search.SearchState;

import java.util.HashMap;
import java.util.Map;

public class StockPresenter implements StockOutputBoundary {
    private final StockViewModel stockViewModel;
    private final ViewManagerModel viewMan;
    private final LoggedInViewModel loggedInViewModel;
    private final SearchState searchState;

    public StockPresenter(StockViewModel stockViewModel, ViewManagerModel viewMan,
                          LoggedInViewModel loggedInViewModel, SearchState searchState) {
        this.stockViewModel = stockViewModel;
        this.viewMan = viewMan;
        this.loggedInViewModel = loggedInViewModel;
        this.searchState = searchState;
    }

    public Map<String, String> extractSearchState(SearchState searchState, StockOutputData stockOD) {
        Map<String, Stock_Search> searchResults = searchState.getSearchResults();
        Stock_Search searchObj = searchResults.get(stockOD.getSymbol());

        Map<String, String> toReturn = new HashMap<>();
        toReturn.put("country", searchObj.getCountry());
        toReturn.put("company", searchObj.getCompany());

        return toReturn;
    }

    @Override
    public void prepareSuccessView(StockOutputData stockOD, PortfolioOutputData portOD) {
        StockState stockState = stockViewModel.getState();
        stockState.setPrice(stockOD.getPrice());
        stockState.setYtdprice(stockOD.getYtdprice());
        stockState.setPerformance(stockOD.getPerformance());
        stockState.setSymbol(stockOD.getSymbol());
        stockState.setValue(portOD.getValue());
        try {
            stockState.setSharesOwned((Integer) portOD.getHoldings().get(stockOD.getSymbol()));
        } catch (Exception e) {
            stockState.setSharesOwned(0);
        }

        stockViewModel.firePropertyChange();
        viewMan.setState(stockViewModel.getViewName());
        viewMan.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        final LoggedInState loginState = loggedInViewModel.getState();
        loginState.setLoggedInError(message);
        stockViewModel.firePropertyChange();
    }
}
