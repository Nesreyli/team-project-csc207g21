package InterfaceAdapter.portfolio;

import InterfaceAdapter.usersession.UserSessionViewModel;
import UseCase.portfolio.PortfolioOutputBoundary;
import UseCase.portfolio.PortfolioOutputData;

public class PortfolioPresenter implements PortfolioOutputBoundary {

    private final PortfolioViewModel portfolioViewModel;
    private final UserSessionViewModel userSessionViewModel;

    public PortfolioPresenter(PortfolioViewModel portfolioViewModel,
                              UserSessionViewModel userSessionViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        this.userSessionViewModel = userSessionViewModel;
    }

    @Override
    public void prepareSuccessView(PortfolioOutputData portfolioOutputData) {
        PortfolioViewModel.PortfolioUIState uiState = convertToViewModel(portfolioOutputData);
        portfolioViewModel.setState(uiState);
        portfolioViewModel.firePropertyChange(); // triggers UI update
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Optional: reset portfolio state or show error
        portfolioViewModel.setState(new PortfolioViewModel.PortfolioUIState(
                portfolioViewModel.getState().cashBalance(),
                portfolioViewModel.getState().totalPortfolioValue(),
                portfolioViewModel.getState().performancePercentage(),
                portfolioViewModel.getState().holdings() // keep previous holdings
        ));
        portfolioViewModel.firePropertyChange();
    }

    public static PortfolioViewModel.PortfolioUIState convertToViewModel(PortfolioOutputData response) {
        return new PortfolioViewModel.PortfolioUIState(
                response.getCash(),
                response.getValue(),
                response.getPerformance(),
                response.getHoldings()
        );
    }
}
