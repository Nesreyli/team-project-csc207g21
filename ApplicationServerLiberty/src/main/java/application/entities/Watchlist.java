package application.entities;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    private int userId;
    private List<WatchlistItem> items;

    public Watchlist(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public List<WatchlistItem> getItems() {
        return items;
    }

    /**
     * Adds a new item to the user's watchlist.
     * @param symbol the stock symbol to add
     */
    public void addItem(String symbol) {
        items.add(new WatchlistItem(userId, symbol));
    }

    /**
     * Removes an item from the user's watchlist by symbol.
     * If the symbol does not exist in the watchlist, no action is taken.
     * @param symbol the stock symbol to remove
     */
    public void removeItem(String symbol) {
        items.removeIf(item -> item.getSymbol().equals(symbol));
    }
}
