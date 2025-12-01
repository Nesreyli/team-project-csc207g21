package use_case.portfolio;

public interface PortfolioInputBoundary {
    /**
     * Executes the Portfolio use case using the provided input data.
     * @param input the data containing the username and password for retrieving the portfolio
     */
    void execute(PortfolioInputData input);
}
