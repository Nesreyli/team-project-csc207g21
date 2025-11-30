package interface_adapter.portfolio;

import interface_adapter.ViewModel;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
    public PortfolioViewModel() {
        super("portfolio");
        setState(new PortfolioState());
    }
}
