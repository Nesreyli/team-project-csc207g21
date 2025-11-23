package UseCase.stock;

import Entity.Stock;

public class StockOutputDataFactory {
    public static StockOutputData create(Stock stock) {
        return new StockOutputData(stock.getPrice(), stock.getSymbol(),
                stock.getYtdPrice(), stock.getPerformance());
    }
}
