package UseCase.portfolio;

import UseCase.Login.LoginOutputData;

/**
 * The output boundary for the Login Use Case.
 */
public interface PortfolioOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(PortfolioOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
