package use_case.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import entity.WatchlistEntry;

/**
 * Factory class responsible for creating WatchlistOutputData
 */

public class WatchlistOutputDataFactory {

    /**
     * Creates a WatchlistOutputData object based on the user's credentials
     * and their watchlist entries.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param entries  the list of WatchlistEntry entities representing the watchlist
     * @return a populated WatchlistOutputData object
     */
    public static WatchlistOutputData create(String username, String password, List<WatchlistEntry> entries) {
        final List<String> symbols = entries.stream()
                .map(WatchlistEntry::getSymbol)
                .collect(Collectors.toList());

        final Map<String, BigDecimal> prices = entries.stream()
                .filter(entry -> entry.getPrice() != null)
                .collect(Collectors.toMap(WatchlistEntry::getSymbol, WatchlistEntry::getPrice));

        final Map<String, BigDecimal> performance = entries.stream()
                .filter(entry -> entry.getPerformance() != null)
                .collect(Collectors.toMap(WatchlistEntry::getSymbol, WatchlistEntry::getPerformance));

        return new WatchlistOutputData(username, password, symbols, prices, performance);
    }
}
