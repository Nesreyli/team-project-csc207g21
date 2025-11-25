package UseCase.portfolio;

public interface PortfolioOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param portfolioOutputData the output data
     */
    void prepareSuccessView(PortfolioOutputData portfolioOutputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);


}
