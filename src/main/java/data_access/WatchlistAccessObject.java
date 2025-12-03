package data_access;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.WatchlistEntry;
import okhttp3.*;
import use_case.watchlist.WatchlistAccessInterface;

/**
 * An Access Object handling the Watchlist functionalities.
 */

public class WatchlistAccessObject implements WatchlistAccessInterface {

<<<<<<< HEAD
    private static final String url = "http://localhost:8080/rest";
=======
    private static final String url = "http://100.67.4.80:4848/rest";
>>>>>>> origin/main
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<WatchlistEntry> getWatchlist(String username, String password) {
        final List<WatchlistEntry> watchlist = new ArrayList<>();

        final Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/get?username=%s&password=%s", username, password))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            final Response httpResponse = client.newCall(request).execute();
            final String jsonResponse = httpResponse.body().string();
            httpResponse.close();

            final JSONObject body = new JSONObject(jsonResponse);
            final int message = body.optInt("message", 400);

            if (message == 200) {
                final JSONArray symbolsJson = body.optJSONArray("symbols");
                final JSONObject pricesJson = body.optJSONObject("prices");

                if (symbolsJson != null) {
                    for (int i = 0; i < symbolsJson.length(); i++) {
                        final String symbol = symbolsJson.getString(i);
                        BigDecimal price = BigDecimal.ZERO;
                        BigDecimal perf = BigDecimal.ZERO;

                        if (pricesJson != null && pricesJson.has(symbol)) {
                            final JSONObject stock = pricesJson.getJSONObject(symbol);
                            if (stock.has("p") && !stock.isNull("p")) {
                                price = BigDecimal.valueOf(stock.getDouble("p")).setScale(2, BigDecimal.ROUND_HALF_UP);
                            }
                            if (stock.has("perf") && !stock.isNull("perf")) {
                                perf = BigDecimal.valueOf(stock.getDouble("perf")).setScale(2, BigDecimal.ROUND_HALF_UP);
                            }
                        }

                        final WatchlistEntry entry = new WatchlistEntry(symbol, price, perf);
                        watchlist.add(entry);
                    }
                }
            }

            return watchlist;

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToWatchlist(String username, String password, String symbol) {

        final Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/add?username=%s&password=%s&symbol=%s",
                        username, password, symbol))
                .post(RequestBody.create(new byte[0]))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            final Response response = client.newCall(request).execute();
            response.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFromWatchlist(String username, String password, String symbol) {
        final Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/remove?username=%s&password=%s&symbol=%s",
                        username, password, symbol))
                .delete()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            final Response response = client.newCall(request).execute();
            response.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
