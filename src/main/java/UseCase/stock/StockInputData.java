package UseCase.stock;

public class StockInputData {
    private final String symbol;

    public StockInputData(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
