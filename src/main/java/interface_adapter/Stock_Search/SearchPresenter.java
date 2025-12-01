package interface_adapter.Stock_Search;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.homebutton.HomeOutputBoundary;
import use_case.homebutton.HomeOutputData;
import use_case.stock_search.SearchOutputBoundary;
import use_case.stock_search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary, HomeOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public SearchPresenter(SearchViewModel searchViewModel,
                           ViewManagerModel viewManagerModel,
                           LoggedInViewModel loggedInViewModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData) {
        final SearchState state = searchViewModel.getState();
        state.setSearchResults(outputData.getStocks());
        state.setSearchQuery(outputData.getQuery());
        state.setSearchError("");
        state.setLoading(false);

        searchViewModel.setState(state);
        searchViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SearchState state = searchViewModel.getState();
        state.setSearchError(errorMessage);
        state.setLoading(false);

        searchViewModel.setState(state);
        searchViewModel.firePropertyChange();
    }

    @Override
    public void preparePreviousView(HomeOutputData output) {
        searchViewModel.setState(new SearchState());
        searchViewModel.firePropertyChange();

        loggedInViewModel.getState().setUsername(output.getUsername());
        loggedInViewModel.getState().setPassword(output.getPassword());
        loggedInViewModel.firePropertyChange();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
