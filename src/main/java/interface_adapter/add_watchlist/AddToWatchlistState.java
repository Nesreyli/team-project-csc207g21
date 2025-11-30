package interface_adapter.add_watchlist;

public class AddToWatchlistState {
    private String lastAddedSymbol;
    private String lastMessage;

    public String getLastAddedSymbol() { return lastAddedSymbol; }
    public void setLastAddedSymbol(String lastAddedSymbol) { this.lastAddedSymbol = lastAddedSymbol; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
}
