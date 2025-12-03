package interface_adapter.add_watchlist;

/**
 * The State for the Add to Watchlist Use Case.
 */

public class AddToWatchlistState {
    private String lastAddedSymbol;
    private String lastMessage;

<<<<<<< HEAD
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
=======
    public String getLastAddedSymbol() {
        return lastAddedSymbol;
    }

    public void setLastAddedSymbol(String lastAddedSymbol) {
        this.lastAddedSymbol = lastAddedSymbol;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
>>>>>>> origin/main
}
