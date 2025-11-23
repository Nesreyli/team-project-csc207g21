package UseCase.stock;

import Entity.Response;
import Entity.StockResponse;

public class StockInteractor implements StockInputBoundary {
    private StockAccessInterface stockDB;
    private StockOutputBoundary stockOB;

    public StockInteractor(StockAccessInterface stockDB, StockOutputBoundary stockOB) {
        this.stockDB = stockDB;
        this.stockOB = stockOB;
    }

    public void execute(StockInputData input) {
        Response response = stockDB.getTransactionData(input.getUsername(), input.getPassword(), input.getSymbol());
        StockResponse stockResponse = (StockResponse) response.getEntity();

        switch(response.getStatus_code()){
            case 200:
                StockOutputData stockOutputData =
                        StockOutputDataFactory.create(input.getUsername(), input.getPassword(), stockResponse);
                stockOB.prepareSuccessView(stockOutputData);
                break;
            case 400:
                stockOB.prepareFailView("Incorrect username or password.");
                break;
            case 500:
                stockOB.prepareFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }
}
