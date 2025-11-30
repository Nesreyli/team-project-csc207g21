package InterfaceAdapter.buySell;

import Entity.Stock_Search;
import InterfaceAdapter.Stock_Search.SearchState;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import UseCase.buySell.BuySellOutputBoundary;
import UseCase.buySell.BuySellOutputData;
import UseCase.portfolio.PortfolioOutputData;
import UseCase.stock.StockOutputBoundary;
import UseCase.stock.StockOutputData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BuySellPresenter implements BuySellOutputBoundary {
    private final BuySellViewModel buySellViewModel;
    private final ViewManagerModel viewMan;

    public BuySellPresenter(BuySellViewModel buySellViewModel, ViewManagerModel viewMan) {
        this.buySellViewModel = buySellViewModel;
        this.viewMan = viewMan;
    }

    @Override
    public void prepareSuccessView(BuySellOutputData buySellOD) {
        BuySellState bsState = buySellViewModel.getState();
        bsState.setOrder(buySellOD.getOrder());
        bsState.setSymbol(buySellOD.getSymbol());
        bsState.setPrice(buySellOD.getPrice());
        bsState.setPrice(buySellOD.getPrice());
        bsState.setTotalPrice(buySellOD.getPrice());

        buySellViewModel.firePropertyChange();
        viewMan.setState(buySellViewModel.getViewName());
        viewMan.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        BuySellState bsState = buySellViewModel.getState();

        if (Objects.equals(message, "Server Error")) {
            bsState.setErrorMessage(message);
        } else if (bsState.getOrder().equals('b')) {
            bsState.setErrorMessage("Inadequate funds for purchase.");
        } else {
            bsState.setErrorMessage("Inadequate shares owned for sale.");
        }

        buySellViewModel.firePropertyChange();
    }
}
