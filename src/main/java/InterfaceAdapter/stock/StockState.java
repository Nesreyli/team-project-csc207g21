package InterfaceAdapter.stock;

public class StockState {
    String stockName;
    String stockDetails;
    Double stockPrice;
    Integer ownedStock;

    public StockState(String stockName, String stockDetails, Double stockPrice, int ownedStock) {
        this.stockName = stockName;
        this.stockDetails = stockDetails;
        this.stockPrice = stockPrice;
        this.ownedStock = ownedStock;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockDetails() {
        return stockDetails;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public int getOwnedStock() {
        return ownedStock;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setStockDetails(String stockDetails) {
        this.stockDetails = stockDetails;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public void setOwnedStock(int ownedStock) {
        this.ownedStock = ownedStock;
    }
}
