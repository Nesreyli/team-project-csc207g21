package application.use_case.watchlist;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import application.use_case.Price.OutputDataPrice;
import application.use_case.Price.PricesInput;
import application.use_case.Price.getPriceInteractor;
import application.use_case.User.UserDatabaseInterface;

@Singleton
public class WatchlistInteractor {

    @Inject
    private WatchlistDatabaseInterface watchlistDB;

    @Inject
    private getPriceInteractor priceInteractor;

    @Inject
    private UserDatabaseInterface userDB;

    /**
     * Interactor responsible for managing user watchlist, including retrieving
     * watchlist data, adding new symbols, and removing existing ones.
     */
    public OutputWatchlist executeWatchlist(String username, String password, boolean includePrices) {
        final Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            final List<WatchlistEntry> entries = watchlistDB.getWatchlistForUser(userId);
            final List<String> symbols = entries.stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            if (includePrices && !symbols.isEmpty()) {
                final PricesInput priceInput = new PricesInput(String.join(",", symbols));
                final OutputDataPrice priceData = priceInteractor.executePrice(priceInput);

                if (!"200".equals(priceData.getMessage())) {
                    return new OutputWatchlist("400");
                }

                final Map<String, Map<String, BigDecimal>> pricesMap = priceData.getPrices();
                return new OutputWatchlist("200", username, symbols, pricesMap);
            }
            else {
                return new OutputWatchlist("200", username, symbols);
            }

        }
        catch (RuntimeException error) {
            return new OutputWatchlist("400");
        }
    }

    public OutputWatchlist addStock(String username, String password, String symbol) {
        final Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            watchlistDB.addToWatchlist(userId, symbol);

            final List<String> symbols = watchlistDB.getWatchlistForUser(userId)
                    .stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            return new OutputWatchlist("200", username, symbols);

        }
        catch (RuntimeException error) {
            return new OutputWatchlist("400");
        }
    }

    public OutputWatchlist removeStock(String username, String password, String symbol) {
        final Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            watchlistDB.removeFromWatchlist(userId, symbol);

            final List<String> symbols = watchlistDB.getWatchlistForUser(userId)
                    .stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            return new OutputWatchlist("200", username, symbols);
        }
        catch (RuntimeException error) {
            return new OutputWatchlist("400");
        }
    }
}
