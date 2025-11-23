package UseCase.stock;

import Entity.Stock;

public class StockOutputDataFactory {
    public static StockOutputData create(String username, String password, Stock stock) {
        return new StockOutputData(username, password,
                stock.getOwned(), stock.getPrice(), stock.getValue(),
                stock.getCompany(), stock.getCountry());
    }
}
