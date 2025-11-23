package InterfaceAdapter.stock;

import InterfaceAdapter.ViewModel;
import jakarta.ejb.Singleton;

@Singleton
public class StockViewModel extends ViewModel<StockState> {
    public StockViewModel() {
        super("Stock View");
        setState(new StockState());
    }
}
