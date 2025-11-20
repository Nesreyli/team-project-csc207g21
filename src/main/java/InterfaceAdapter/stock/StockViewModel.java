package InterfaceAdapter.stock;

import InterfaceAdapter.ViewModel;
import jakarta.ejb.Singleton;

@Singleton
public class StockViewModel extends ViewModel<StockState> {
    public StockViewModel() {
        super("Stock View");

        // Initial values
        String stockName = "No Stock Selected";
        String stockDetails = "N/A";
        Double stockPrice = 0.0;
        Integer ownedStock = 0;

        setState(new StockState(stockName, stockDetails, stockPrice, ownedStock));
    }

    public void setStockState(String stockName, String stockDetails, Double stockPrice, int ownedStock) {
        setState(new StockState(stockName, stockDetails, stockPrice, ownedStock));
    }
}
