package use_case.remove_watchlist;

import entity.WatchlistEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory class responsible for creating RemoveFromWatchlistOutputData
 */

public class RemoveFromWatchlistOutputDataFactory {

    /**
     * Creates a RemoveFromWatchlistOutputData object.
     *
     * @param removedSymbol The symbol that was removed from the watchlist
     * @param entries       The current list of WatchlistEntry entities after removal
     * @return a populated RemoveFromWatchlistOutputData object
     */
    public static RemoveFromWatchlistOutputData create(String removedSymbol, List<WatchlistEntry> entries) {
        if (entries == null) entries = new ArrayList<>();

        List<String> updatedSymbols = entries.stream()
                .map(WatchlistEntry::getSymbol)
                .collect(Collectors.toList());

        return new RemoveFromWatchlistOutputData("200", removedSymbol, updatedSymbols);
    }
}
