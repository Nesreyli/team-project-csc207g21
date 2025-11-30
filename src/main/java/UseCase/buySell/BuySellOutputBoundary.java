package UseCase.buySell;

public interface BuySellOutputBoundary {
    void prepareSuccessView(BuySellOutputData buySellOD);
    void prepareFailView(String message);
}
