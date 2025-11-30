package application.use_case.watchlist;

import application.use_case.Price.OutputDataPrice;
import application.use_case.Price.PricesInput;
import application.use_case.Price.getPriceInteractor;
import application.use_case.User.UserDatabaseInterface;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class WatchlistInteractor {

    @Inject
    WatchlistDatabaseInterface watchlistDB;

    @Inject
    getPriceInteractor priceInteractor;

    @Inject
    UserDatabaseInterface userDB;

    public OutputWatchlist executeWatchlist(String username, String password, boolean includePrices) {
        Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            List<WatchlistEntry> entries = watchlistDB.getWatchlistForUser(userId);
            List<String> symbols = entries.stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            if (includePrices && !symbols.isEmpty()) {
                PricesInput priceInput = new PricesInput(String.join(",", symbols));
                OutputDataPrice priceData = priceInteractor.executePrice(priceInput);

                if (!"200".equals(priceData.getMessage())) {
                    return new OutputWatchlist("400");
                }

                Map<String, Map<String, BigDecimal>> pricesMap = priceData.getPrices();
                return new OutputWatchlist("200", username, symbols, pricesMap);
            } else {
                return new OutputWatchlist("200", username, symbols);
            }

        } catch (RuntimeException e) {
            return new OutputWatchlist("400");
        }
    }

    public OutputWatchlist addStock(String username, String password, String symbol) {
        Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            watchlistDB.addToWatchlist(userId, symbol);

            List<String> symbols = watchlistDB.getWatchlistForUser(userId)
                    .stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            return new OutputWatchlist("200", username, symbols);

        } catch (RuntimeException e) {
            return new OutputWatchlist("400");
        }
    }

    public OutputWatchlist removeStock(String username, String password, String symbol) {
        Integer userId;
        try {
            userId = userDB.getUserID(username, password);
            watchlistDB.removeFromWatchlist(userId, symbol);

            List<String> symbols = watchlistDB.getWatchlistForUser(userId)
                    .stream()
                    .map(WatchlistEntry::getSymbol)
                    .collect(Collectors.toList());

            return new OutputWatchlist("200", username, symbols);

        } catch (RuntimeException e) {
            return new OutputWatchlist("400");
        }
    }
}
