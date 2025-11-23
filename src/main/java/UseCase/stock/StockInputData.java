package UseCase.stock;

public class StockInputData {
    private final String username;
    private final String password;
    private final String symbol;

    public StockInputData(String username, String password, String symbol) {
        this.symbol = symbol;
        this.username = username;
        this.password = password;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
