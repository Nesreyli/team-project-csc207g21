package interface_adapter.homebutton;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.homebutton.HomeOutputBoundary;
import use_case.homebutton.HomeOutputData;

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
    public void preparePreviousView(HomeOutputData output) {

        LoggedInState loggedInState = loggedViewModel.getState();
        loggedInState.setUsername(output.getUsername());
        loggedInState.setPassword(output.getPassword());
        loggedViewModel.firePropertyChange();

//        portViewModel.setState(new PortfolioState());

        viewManagerModel.setState(loggedViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
