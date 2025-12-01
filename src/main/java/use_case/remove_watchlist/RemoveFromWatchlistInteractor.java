package use_case.remove_watchlist;

import java.util.List;
import java.util.stream.Collectors;

import use_case.watchlist.WatchlistAccessInterface;

public class RemoveFromWatchlistInteractor implements RemoveFromWatchlistInputBoundary {

    private final WatchlistAccessInterface watchlistAccess;
    private final RemoveFromWatchlistOutputBoundary output;

    public RemoveFromWatchlistInteractor(WatchlistAccessInterface watchlistAccess,
                                         RemoveFromWatchlistOutputBoundary output) {
        this.watchlistAccess = watchlistAccess;
        this.output = output;
    }

    @Override
    public void execute(RemoveFromWatchlistInputData inputData) {
        try {
            watchlistAccess.removeFromWatchlist(inputData.getUsername(), inputData.getPassword(),
                    inputData.getSymbol());

            final List<String> updatedSymbols = watchlistAccess.getWatchlist(inputData.getUsername(),
                            inputData.getPassword())
                    .stream()
                    .map(entry -> entry.getSymbol())
                    .collect(Collectors.toList());

            final RemoveFromWatchlistOutputData out = new RemoveFromWatchlistOutputData("Successfully removed",
                    inputData.getSymbol(), updatedSymbols);
            output.prepareRemoveFromWatchlistSuccessView(out);

        }
        catch (Exception error) {
            output.prepareRemoveFromWatchlistFailView("Failed to remove symbol: " + error.getMessage());
        }
    }
}
