package InterfaceAdapter.Search;

import InterfaceAdapter.ViewManagerModel;
import UseCase.Search.SearchOutputBoundary;
import UseCase.Search.SearchOutputData;

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
        state.setSearchResult(outputData.getStocks());
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
