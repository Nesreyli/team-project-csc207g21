package interface_adapter.buySell;

import entity.Stock_Search;
import interface_adapter.Stock_Search.SearchState;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.buySell.BuySellOutputBoundary;
import use_case.buySell.BuySellOutputData;
import use_case.portfolio.PortfolioOutputData;

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
        bsState.setAmount(buySellOD.getAmount());
        bsState.setTotalPrice(buySellOD.getTotalPrice());
        bsState.setErrorMessage("");
        buySellViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        BuySellState bsState = buySellViewModel.getState();

        bsState.setErrorMessage(message);
        buySellViewModel.firePropertyChange("error");
    }
}
