package application.entities;

public class WatchlistItem {
    private int id;
    private int userId;
    private String symbol;

    public WatchlistItem(int id, int userId, String symbol) {
        this.id = id;
        this.userId = userId;
        this.symbol = symbol;
    }

    public WatchlistItem(int userId, String symbol) {
        this.userId = userId;
        this.symbol = symbol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
}
