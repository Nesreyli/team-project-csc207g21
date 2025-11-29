// Application/UseCases/watchlist/WatchlistInteractor.java
package Application.UseCases.watchlist;

import Application.UseCases.Price.OutputDataPrice;
import Application.UseCases.Price.PricesInput;
import Application.UseCases.Price.getPriceInteractor;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class WatchlistInteractor {

    @Inject
    WatchlistDatabaseInterface watchlistDB;

    @Inject
    getPriceInteractor priceInteractor;

    public OutputWatchlist executeWatchlistByUserId(int userId, boolean includePrices) {
        try {
            List<WatchlistEntry> entries = watchlistDB.getWatchlistForUser(userId);
            List<String> symbols = entries.stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            if (includePrices && !symbols.isEmpty()) {
                PricesInput input = new PricesInput(String.join(",", symbols));
                OutputDataPrice priceData = priceInteractor.executePrice(input);
                Map<String, Map<String, java.math.BigDecimal>> prices = priceData.getPrices();
                return new OutputWatchlist("200", null, symbols, prices);
            } else {
                return new OutputWatchlist("200", null, symbols);
            }
        } catch (RuntimeException e) {
            return new OutputWatchlist("500");
        }
    }

    public WatchlistEntry addStockToWatchlist(int userId, String symbol) {
        watchlistDB.addToWatchlist(userId, symbol);
        return new WatchlistEntry(symbol);
    }

    public void removeStockFromWatchlist(int userId, String symbol) {
        watchlistDB.removeFromWatchlist(userId, symbol);
    }

    public List<WatchlistEntry> getWatchlistForUser(int userId) {
        return watchlistDB.getWatchlistForUser(userId);
    }
}
