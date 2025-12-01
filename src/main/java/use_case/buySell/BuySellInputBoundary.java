package use_case.buySell;

public interface BuySellInputBoundary {
    /**
     * Executes the Buy/Sell use case with the provided input data.
     *
     * @param input the data necessary to perform a buy or sell operation
     */
    void execute(BuySellInputData input);
}
