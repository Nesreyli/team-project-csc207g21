package InterfaceAdapter.removeFromWatchlist;

import UseCase.removeFromWatchlist.RemoveFromWatchlistInputBoundary;
import UseCase.removeFromWatchlist.RemoveFromWatchlistInputData;

public class RemoveFromWatchlistController {
    private final RemoveFromWatchlistInputBoundary inputBoundary;

    public RemoveFromWatchlistController(RemoveFromWatchlistInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void remove(String username, String password, String symbol) {
        RemoveFromWatchlistInputData data = new RemoveFromWatchlistInputData(username, password, symbol);
        inputBoundary.execute(data);
    }
}
