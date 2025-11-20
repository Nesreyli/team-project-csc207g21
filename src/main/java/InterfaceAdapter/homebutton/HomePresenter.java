package InterfaceAdapter.homebutton;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.portfolio.PortfolioState;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import UseCase.homebutton.HomeOutputBoundary;
import UseCase.homebutton.HomeOutputData;

public class HomePresenter implements HomeOutputBoundary {
    private PortfolioViewModel portViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedViewModel;

    public HomePresenter(PortfolioViewModel portViewModel, ViewManagerModel viewManagerModel
                                   , LoggedInViewModel loggedInViewModel) {
        this.portViewModel = portViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessview(HomeOutputData output) {
        PortfolioState portfolioState = portViewModel.getState();

        LoggedInState loggedInState = loggedViewModel.getState();
        loggedInState.setUsername(portfolioState.getUsername());
        loggedInState.setPassword(portfolioState.getPassword());
        loggedViewModel.firePropertyChange();

        portViewModel.setState(new PortfolioState());

        viewManagerModel.setState(loggedViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
