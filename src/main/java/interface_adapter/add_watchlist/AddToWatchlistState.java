package interface_adapter.add_watchlist;

public class AddToWatchlistState {
    private String lastAddedSymbol;
    private String lastMessage;

    /**
     * Getter.
     * @return last added symbol
     */
    public String getLastAddedSymbol() { return lastAddedSymbol; }

    /**
     * Setter.
     * @param lastAddedSymbol last added symbol
     */
    public void setLastAddedSymbol(String lastAddedSymbol) { this.lastAddedSymbol = lastAddedSymbol; }

    /**
     * Getter.
     * @return lastMessage
     */
    public String getLastMessage() { return lastMessage; }

    /**
     * Setter.
     * @param lastMessage last message
     */
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
}
