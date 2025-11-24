package UseCase.Stock_Search;

public interface SearchOutputBoundary {
    /**
     * Prepares the success view for the Search Use Case.
     * @param outputData the output data containing search results
     */
    void prepareSuccessView(SearchOutputData outputData);

    /**
     * Prepares the failure view for the Search Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
