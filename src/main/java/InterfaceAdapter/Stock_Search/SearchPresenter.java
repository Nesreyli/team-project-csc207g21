package InterfaceAdapter.Stock_Search;

import Entity.Stock_Search;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import UseCase.Stock_Search.SearchOutputBoundary;
import UseCase.Stock_Search.SearchOutputData;
import UseCase.homebutton.HomeOutputBoundary;
import UseCase.homebutton.HomeOutputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void prepareSuccessView(SearchOutputData outputData){
        final SearchState state = searchViewModel.getState();
        state.setSearchResults(outputData.getStocks());
        state.setSearchQuery(outputData.getQuery());
        state.setSearchError("");
        state.setLoading(false);

        searchViewModel.setState(state);
        searchViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage){
        final SearchState state = searchViewModel.getState();
        state.setSearchError(errorMessage);
        state.setLoading(false);

        searchViewModel.setState(state);
        searchViewModel.firePropertyChange();
    }

    @Override
    public void preparePreviousView(HomeOutputData output){
        searchViewModel.setState(new SearchState());
        searchViewModel.firePropertyChange();

        loggedInViewModel.getState().setUsername(output.getUsername());
        loggedInViewModel.getState().setPassword(output.getPassword());
        loggedInViewModel.firePropertyChange();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
