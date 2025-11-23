package UseCase.stock;

public interface StockAccessInterface {
    public Entity.Response getTransactionData(String username, String password, String symbol);
}
