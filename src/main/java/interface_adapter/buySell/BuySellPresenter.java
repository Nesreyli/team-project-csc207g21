package interface_adapter.buySell;

import interface_adapter.ViewManagerModel;
import use_case.buySell.BuySellOutputBoundary;
import use_case.buySell.BuySellOutputData;

/**
 * The Presenter of the buy/sell use case, which tells the View Model which view to prepare (success/failure).
 */
public class BuySellPresenter implements BuySellOutputBoundary {
    private final BuySellViewModel buySellViewModel;
    private final ViewManagerModel viewMan;

    public BuySellPresenter(BuySellViewModel buySellViewModel, ViewManagerModel viewMan) {
        this.buySellViewModel = buySellViewModel;
        this.viewMan = viewMan;
    }

    /**
     * Presents success view.
     * @param buySellOd the output data containing details of the completed transaction
     */
    @Override
    public void prepareSuccessView(BuySellOutputData buySellOd) {
        BuySellState bsState = buySellViewModel.getState();
        bsState.setOrder(buySellOd.getOrder());
        bsState.setSymbol(buySellOd.getSymbol());
        bsState.setPrice(buySellOd.getPrice());
        bsState.setAmount(buySellOd.getAmount());
        bsState.setTotalPrice(buySellOd.getTotalPrice());
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
