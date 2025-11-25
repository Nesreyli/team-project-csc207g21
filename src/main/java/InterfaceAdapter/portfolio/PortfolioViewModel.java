package InterfaceAdapter.portfolio;

import InterfaceAdapter.ViewModel;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
    public PortfolioViewModel() {
        super("portfolio");
        setState(new PortfolioState());
    }
}