package InterfaceAdapter.logged_in;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.portfolio.PortfolioState;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import UseCase.portfolio.PortfolioOutputBoundary;
import UseCase.portfolio.PortfolioOutputData;

// Should this be Portfolio Presenter or stay here as it
// is in currently in logged in menu which changes flow to portfolio view
public class LoggedInPresenter implements PortfolioOutputBoundary {
    private final PortfolioViewModel portViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;


    public LoggedInPresenter(ViewManagerModel viewManagerModel,
                          PortfolioViewModel portViewModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portViewModel = portViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void preparePortSuccessView(PortfolioOutputData response) {
        // On success, update the loggedInViewModel's state
        final PortfolioState portfolioState = portViewModel.getState();
        portfolioState.setUsername(response.getUsername());
        portfolioState.setPassword(response.getPassword());
        portfolioState.setPerformance(response.getPerformance().toString() + "%");
        portfolioState.setCash(response.getCash().toString() + " USD");
        portfolioState.setValue(response.getValue().toString() + " USD");
        portfolioState.setHoldings(response.getHoldings());
        this.portViewModel.firePropertyChange();

        // and clear everything from the LoginViewModel's state
        loggedInViewModel.setState(new LoggedInState());

        // switch to the logged in view
        this.viewManagerModel.setState(portViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void preparePortFailView(String error) {
        final LoggedInState loginState = loggedInViewModel.getState();
        loginState.setLoggedInError(error);
        loggedInViewModel.firePropertyChange();
    }
}

