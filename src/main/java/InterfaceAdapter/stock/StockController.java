package InterfaceAdapter.stock;

import UseCase.stock.StockInputBoundary;
import UseCase.stock.StockInputData;

public class StockController {

    private final StockInputBoundary stockInteractor;

    public StockController(StockInputBoundary stockInteractor) {
        this.stockInteractor = stockInteractor;
    }

    /**
     * Executes the Login Use Case.
     */
    public void execute(String symbol) {
        final StockInputData stockInputData = new StockInputData(symbol);

        stockInteractor.execute(stockInputData);
    }
}