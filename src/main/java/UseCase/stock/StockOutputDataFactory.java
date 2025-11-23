package UseCase.stock;

import Entity.StockResponse;

public class StockOutputDataFactory {
    public static StockOutputData create(String username, String password, StockResponse stockResponse) {
        return new StockOutputData(username, password,
                stockResponse.getOwned(), stockResponse.getPrice(), stockResponse.getValue(),
                stockResponse.getCompany(), stockResponse.getCountry());
    }
}
