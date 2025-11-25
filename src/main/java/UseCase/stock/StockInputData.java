package UseCase.stock;

public class StockInputData {
    private final String symbol;
    private final String username;
    private final String password;

    public StockInputData(String symbol, String username, String password) {
        this.symbol = symbol;
        this.username = username;
        this.password = password;
    }

    public String getSymbol() {
        return symbol;
    }

    String getUsername() { return username; }

    String getPassword() { return password; }
}
