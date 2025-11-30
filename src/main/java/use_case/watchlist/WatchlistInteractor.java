package use_case.watchlist;

import entity.WatchlistEntry;

import java.util.ArrayList;
import java.util.List;

public class WatchlistInteractor implements WatchlistInputBoundary {
    private final WatchlistAccessInterface access;
    private final WatchlistOutputBoundary output;

    public WatchlistInteractor(WatchlistAccessInterface access, WatchlistOutputBoundary output) {
        this.access = access;
        this.output = output;
    }

    @Override
    public void execute(WatchlistInputData input) {
        try {
            List<WatchlistEntry> entries = access.getWatchlist(input.getUsername(), input.getPassword());

            if (entries == null) {
                entries = new ArrayList<>();
            }

            WatchlistOutputData outputData = WatchlistOutputDataFactory.create(
                    input.getUsername(),
                    input.getPassword(),
                    entries
            );
            output.prepareWatchlistSuccessView(outputData);
        } catch (Exception e) {
            output.prepareWatchlistFailView("Server error: " + e.getMessage());
        }
    }
}
