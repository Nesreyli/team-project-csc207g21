package InterfaceAdapter.portfolio;

import Application.Entities.Portfolio;
import InterfaceAdapter.ViewModel;
import jakarta.ejb.Singleton;

@Singleton
public class PortfolioViewModel extends ViewModel<PortfolioState> {
    public PortfolioViewModel() {
        super("Portfolio View");

        // Default state
        setPortfolioState(0.0);
    }

    public void setPortfolioState(Double balance) {
        setState(new PortfolioState(balance));
    }
}
