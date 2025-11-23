package InterfaceAdapter.stock;

import InterfaceAdapter.ViewManagerModel;
import UseCase.stock.StockOutputBoundary;
import UseCase.stock.StockOutputData;

public class StockPresenter implements StockOutputBoundary {
    private final StockViewModel stockViewModel;
    private final ViewManagerModel viewMan;

    public StockPresenter(StockViewModel stockViewModel, ViewManagerModel viewMan) {
        this.stockViewModel = stockViewModel;
        this.viewMan = viewMan;
    }

    @Override
    public void prepareSuccessView(StockOutputData stockOD) {
        StockState stockState = stockViewModel.getState();
        stockState.setOwned(stockOD.getOwned());
        stockState.setPrice(stockOD.getPrice());
        stockState.setValue(stockOD.getValue());
        stockState.setCountry(stockOD.getCountry());
        stockState.setCountry(stockOD.getCountry());

        stockViewModel.firePropertyChange();
        viewMan.setState(stockViewModel.getViewName());
        viewMan.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        StockState stockState = stockViewModel.getState();
        stockState.setError(message);
        stockViewModel.firePropertyChange();
    }
}
