package use_case.add_watchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.WatchlistEntry;

/**
 * Factory class responsible for creating AddToWatchlistOutputData.
 */

public class AddToWatchlistOutputDataFactory {

    /**
     * Creates an AddToWatchlistOutputData object.
     *
     * @param addedSymbol The symbol that was just added to the watchlist
     * @param entries     The current list of WatchlistEntry entities after addition
     * @return a populated AddToWatchlistOutputData object
     */
    public static AddToWatchlistOutputData create(String addedSymbol, List<WatchlistEntry> entries) {
        if (entries == null) {
            entries = new ArrayList<>();
        }

        final List<String> updatedSymbols = entries.stream()
                .map(WatchlistEntry::getSymbol)
                .collect(Collectors.toList());

        return new AddToWatchlistOutputData("200", addedSymbol, updatedSymbols);
    }
}
