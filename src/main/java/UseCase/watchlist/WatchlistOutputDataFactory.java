package UseCase.watchlist;

import Entity.WatchlistEntry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WatchlistOutputDataFactory {
    public static WatchlistOutputData create(String username, String password, List<WatchlistEntry> entries) {
        List<String> symbols = entries.stream()
                .map(WatchlistEntry::getSymbol)
                .collect(Collectors.toList());

        Map<String, BigDecimal> prices = entries.stream()
                .filter(e -> e.getPrice() != null)
                .collect(Collectors.toMap(WatchlistEntry::getSymbol, WatchlistEntry::getPrice));

        Map<String, BigDecimal> performance = entries.stream()
                .filter(e -> e.getPerformance() != null)
                .collect(Collectors.toMap(WatchlistEntry::getSymbol, WatchlistEntry::getPerformance));

        return new WatchlistOutputData(username, password, symbols, prices, performance);
    }
}
