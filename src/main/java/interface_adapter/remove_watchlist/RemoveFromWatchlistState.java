package interface_adapter.remove_watchlist;

/**
 * The State for the Remove from Watchlist Use Case.
 */

public class RemoveFromWatchlistState {
    private String lastRemovedSymbol;
    private String lastMessage;

    public String getLastRemovedSymbol() { return lastRemovedSymbol; }
    public void setLastRemovedSymbol(String lastRemovedSymbol) { this.lastRemovedSymbol = lastRemovedSymbol; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
}
