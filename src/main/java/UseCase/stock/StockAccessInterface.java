package UseCase.stock;

public interface StockAccessInterface {
    // TODO: Implement this method in data access class
    public Entity.Response getTransactionData(String username, String password, String symbol);
}
