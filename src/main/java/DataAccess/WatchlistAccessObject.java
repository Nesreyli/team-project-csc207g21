package DataAccess;

import Entity.WatchlistEntry;
import UseCase.watchlist.WatchlistAccessInterface;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WatchlistAccessObject implements WatchlistAccessInterface {

    private static final String url = "http://localhost:8080/rest";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<WatchlistEntry> getWatchlist(String username, String password) {
        Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/get?username=%s&password=%s", username, password))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response httpResponse = client.newCall(request).execute();
            String jsonResponse = httpResponse.body().string();
            httpResponse.close();

            JSONObject body = new JSONObject(jsonResponse);
            List<WatchlistEntry> watchlist = new ArrayList<>();

            if (body.getInt("message") == 200) {
                JSONArray symbolsJson = body.getJSONArray("symbols");
                JSONObject pricesJson = body.getJSONObject("prices");

                for (int i = 0; i < symbolsJson.length(); i++) {
                    String symbol = symbolsJson.getString(i);
                    BigDecimal price = BigDecimal.ZERO;
                    BigDecimal perf = BigDecimal.ZERO;

                    if (pricesJson.has(symbol)) {
                        JSONObject stock = pricesJson.getJSONObject(symbol);

                        if (stock.has("p") && !stock.isNull("p")) {
                            price = BigDecimal.valueOf(stock.getDouble("p")).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }

                        if (stock.has("perf") && !stock.isNull("perf")) {
                            perf = BigDecimal.valueOf(stock.getDouble("perf")).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                    }

                    WatchlistEntry entry = new WatchlistEntry(symbol, price, perf);
                    entry.setPerformance(perf);
                    watchlist.add(entry);
                }
            }

            return watchlist;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addToWatchlist(String username, String password, String symbol) {

        Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/add?username=%s&password=%s&symbol=%s",
                        username, password, symbol))
                .post(RequestBody.create(new byte[0]))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("Server response: " + response.code());
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFromWatchlist(String username, String password, String symbol) {
        Request request = new Request.Builder()
                .url(String.format(url + "/watchlist/remove?username=%s&password=%s&symbol=%s",
                        username, password, symbol))
                .delete()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("Server response (delete): " + response.code());
            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
