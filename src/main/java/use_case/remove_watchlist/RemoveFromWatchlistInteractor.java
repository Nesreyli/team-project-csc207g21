package use_case.remove_watchlist;

import use_case.watchlist.WatchlistAccessInterface;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveFromWatchlistInteractor implements RemoveFromWatchlistInputBoundary {

    private final WatchlistAccessInterface watchlistAccess;
    private final RemoveFromWatchlistOutputBoundary output;

    public RemoveFromWatchlistInteractor(WatchlistAccessInterface watchlistAccess, RemoveFromWatchlistOutputBoundary output) {
        this.watchlistAccess = watchlistAccess;
        this.output = output;
    }

    @Override
    public void execute(RemoveFromWatchlistInputData inputData) {
        try {
            watchlistAccess.removeFromWatchlist(inputData.getUsername(), inputData.getPassword(), inputData.getSymbol());

            List<String> updatedSymbols = watchlistAccess.getWatchlist(inputData.getUsername(), inputData.getPassword())
                    .stream()
                    .map(e -> e.getSymbol())
                    .collect(Collectors.toList());

            RemoveFromWatchlistOutputData out = new RemoveFromWatchlistOutputData("Successfully removed", inputData.getSymbol(), updatedSymbols);
            output.prepareRemoveFromWatchlistSuccessView(out);

        } catch (Exception e) {
            output.prepareRemoveFromWatchlistFailView("Failed to remove symbol: " + e.getMessage());
        }
    }
}
