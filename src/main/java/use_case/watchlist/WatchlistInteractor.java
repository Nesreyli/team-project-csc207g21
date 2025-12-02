package use_case.watchlist;

import java.util.ArrayList;
import java.util.List;

import entity.WatchlistEntry;

/**
 * The Interactor for the Watchlist Use Case.
 */

public class WatchlistInteractor implements WatchlistInputBoundary {

    private final WatchlistAccessInterface access;
    private final WatchlistOutputBoundary output;

    /**
     * Constructs a WatchlistInteractor with the specified access interface
     * and output boundary.
     *
     * @param access the data access interface for watchlist operations
     * @param output the output boundary for presenting watchlist results
     */
    public WatchlistInteractor(WatchlistAccessInterface access, WatchlistOutputBoundary output) {
        this.access = access;
        this.output = output;
    }

    /**
     * Executes the use case: fetches the user's watchlist and
     * presents it via the output boundary.
     *
     * @param input the WatchlistInputData containing username and password
     */
    @Override
    public void execute(WatchlistInputData input) {
        try {
            List<WatchlistEntry> entries = access.getWatchlist(input.getUsername(), input.getPassword());

            if (entries == null) {
                entries = new ArrayList<>();
            }

            final WatchlistOutputData outputData = WatchlistOutputDataFactory.create(
                    input.getUsername(),
                    input.getPassword(),
                    entries
            );
            output.prepareWatchlistSuccessView(outputData);
        }
        catch (Exception error) {
            output.prepareWatchlistFailView("Server error: " + error.getMessage());
        }
    }

    /**
     * Executes the use case and navigates to the watchlist view.
     *
     * @param input the WatchlistInputData containing username and password
     */
    @Override
    public void executeAndNavigate(WatchlistInputData input) {
        execute(input);
        output.switchToWatchlistView();
    }
}
