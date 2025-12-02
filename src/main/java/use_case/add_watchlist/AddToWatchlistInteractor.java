package use_case.add_watchlist;

import java.util.List;

import data_access.WatchlistAccessObject;
import entity.WatchlistEntry;

/**
 * The Interactor for the Add to Watchlist Use Case.
 */

public class AddToWatchlistInteractor implements AddToWatchlistInputBoundary {

    private final WatchlistAccessObject access;
    private final AddToWatchlistOutputBoundary output;

    /**
     * Constructs the interactor with the specified access object and output boundary.
     *
     * @param access the data access object for watchlist operations
     * @param output the output boundary for presenting results
     */
    public AddToWatchlistInteractor(WatchlistAccessObject access, AddToWatchlistOutputBoundary output) {
        this.access = access;
        this.output = output;
    }

    /**
     * Executes the Add to Watchlist use case.
     *
     * @param inputData the input data containing username, password, and symbol
     */
    @Override
    public void execute(AddToWatchlistInputData inputData) {
        try {
            access.addToWatchlist(inputData.getUsername(), inputData.getPassword(), inputData.getSymbol());

            final List<WatchlistEntry> entries = access.getWatchlist(inputData.getUsername(), inputData.getPassword());

            final AddToWatchlistOutputData out = AddToWatchlistOutputDataFactory.create(
                    inputData.getSymbol(), entries
            );

            output.prepareAddToWatchlistSuccessView(out);

        }
        catch (Exception e) {
            output.prepareAddToWatchlistFailView("Failed to add symbol: " + e.getMessage());
        }
    }
}
