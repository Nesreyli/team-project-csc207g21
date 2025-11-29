package InterfaceAdapter.addToWatchlist;

import UseCase.addToWatchlist.AddToWatchlistInputBoundary;
import UseCase.addToWatchlist.AddToWatchlistInputData;

public class AddToWatchlistController {
    private final AddToWatchlistInputBoundary inputBoundary;

    public AddToWatchlistController(AddToWatchlistInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void add(String username, String password, String symbol) {
        AddToWatchlistInputData data = new AddToWatchlistInputData(username, password, symbol);
        inputBoundary.execute(data);
    }
}
