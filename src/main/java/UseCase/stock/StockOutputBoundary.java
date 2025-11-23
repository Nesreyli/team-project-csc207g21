package UseCase.stock;

public interface StockOutputBoundary {
    void prepareSuccessView(StockOutputData stockOD);
    void prepareFailView(String message);
}
