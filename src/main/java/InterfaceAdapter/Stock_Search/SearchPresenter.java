package InterfaceAdapter.Stock_Search;

import Entity.Stock_Search;
import InterfaceAdapter.ViewManagerModel;
import UseCase.Stock_Search.SearchOutputBoundary;
import UseCase.Stock_Search.SearchOutputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData){
        final SearchState state = searchViewModel.getState();
        state.setSearchResults(outputData.getStocks());
        state.setSearchQuery(outputData.getQuery());
        state.setSearchError(null);
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

}
