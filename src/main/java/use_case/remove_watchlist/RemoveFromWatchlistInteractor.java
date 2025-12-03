package use_case.remove_watchlist;

import java.util.List;

import entity.WatchlistEntry;
import use_case.watchlist.WatchlistAccessInterface;

/**
 * The Interactor for the Remove From Watchlist Use Case.
 * Handles removing a symbol from the user's watchlist and preparing the output.
 */

public class RemoveFromWatchlistInteractor implements RemoveFromWatchlistInputBoundary {

    private final WatchlistAccessInterface watchlistAccess;
    private final RemoveFromWatchlistOutputBoundary output;

    /**
     * Constructs the interactor with the specified access interface and output boundary.
     *
     * @param watchlistAccess the data access interface for watchlist operations
     * @param output          the output boundary for presenting the results
     */
    public RemoveFromWatchlistInteractor(WatchlistAccessInterface watchlistAccess,
                                         RemoveFromWatchlistOutputBoundary output) {
        this.watchlistAccess = watchlistAccess;
        this.output = output;
    }

    /**
     * Executes the use case to remove a symbol from a user's watchlist.
     *
     * @param inputData the input data containing the username, password, and symbol
     */
    @Override
    public void execute(RemoveFromWatchlistInputData inputData) {
        try {
            watchlistAccess.removeFromWatchlist(
                    inputData.getUsername(), inputData.getPassword(), inputData.getSymbol());

            final List<WatchlistEntry> entries = watchlistAccess.getWatchlist(
                    inputData.getUsername(), inputData.getPassword());

            final RemoveFromWatchlistOutputData outputData = RemoveFromWatchlistOutputDataFactory.create(
                    inputData.getSymbol(), entries);

            output.prepareRemoveFromWatchlistSuccessView(outputData);

        }
        catch (Exception error) {
            output.prepareRemoveFromWatchlistFailView(
                    "Failed to remove symbol: " + error.getMessage());
        }
    }
}
