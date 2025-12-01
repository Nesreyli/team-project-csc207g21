package use_case.buySell;

public interface BuySellOutputBoundary {
    /**
     * Prepares the view for a successful buy or sell transaction.
     *
     * @param buySellOd the output data containing details of the completed transaction
     */
    void prepareSuccessView(BuySellOutputData buySellOd);

    /**
     * Prepares the view when a buy or sell transaction fails.
     *
     * @param message an explanation of why the operation failed
     */
    void prepareFailView(String message);
}
