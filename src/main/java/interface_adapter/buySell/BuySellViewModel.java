package interface_adapter.buySell;

import interface_adapter.ViewModel;
import jakarta.ejb.Singleton;

@Singleton
public class BuySellViewModel extends ViewModel<BuySellState> {
    public BuySellViewModel() {
        super("Buy/Sell View");
        setState(new BuySellState());
    }
}
