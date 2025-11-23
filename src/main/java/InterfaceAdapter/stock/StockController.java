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
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password, String symbol) {
        final StockInputData stockInputData = new StockInputData(username, password, symbol);

        stockInteractor.execute(stockInputData);
    }
}