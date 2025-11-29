package Application.Entities;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    private int userId;
    private List<WatchlistItem> items;

    public Watchlist(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public List<WatchlistItem> getItems() { return items; }

    public void addItem(String symbol) {
        items.add(new WatchlistItem(userId, symbol));
    }

    public void removeItem(String symbol) {
        items.removeIf(item -> item.getSymbol().equals(symbol));
    }
}
