package UseCase.Stock_Search;

public interface SearchInputBoundary {

    // Executes search use case to load stock given
    void executeSearch(SearchInputData inputData);

    // Ability to load all stocks available
    void executeLoadAll();
}
