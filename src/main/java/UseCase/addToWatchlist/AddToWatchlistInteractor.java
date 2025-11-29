package UseCase.addToWatchlist;

import DataAccess.WatchlistAccessObject;
import Entity.WatchlistEntry;

import java.util.List;
import java.util.stream.Collectors;

public class AddToWatchlistInteractor implements AddToWatchlistInputBoundary {

    private final WatchlistAccessObject access;
    private final AddToWatchlistOutputBoundary output;

    public AddToWatchlistInteractor(WatchlistAccessObject access, AddToWatchlistOutputBoundary output) {
        this.access = access;
        this.output = output;
    }

    @Override
    public void execute(AddToWatchlistInputData inputData) {
        try {
            access.addToWatchlist(inputData.getUsername(), inputData.getPassword(), inputData.getSymbol());
            List<WatchlistEntry> entries = access.getWatchlist(inputData.getUsername(), inputData.getPassword());
            List<String> updatedSymbols = entries.stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());
            AddToWatchlistOutputData out = new AddToWatchlistOutputData(
                    "200",
                    inputData.getSymbol(),
                    updatedSymbols
            );
            output.prepareAddToWatchlistSuccessView(out);
        } catch (Exception e) {
            output.prepareAddToWatchlistFailView("Failed to add symbol: " + e.getMessage());
        }
    }
}
