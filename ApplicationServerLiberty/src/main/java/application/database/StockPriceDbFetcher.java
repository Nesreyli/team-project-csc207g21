package application.database;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONObject;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class StockPriceDbFetcher {
    private static final int SLEEP_TIME = 20000;

    /**
     * Asyncrhonous method running on seperate thread.
     * Uses fire and forget method that polls price data every 20 seconds.
     * @param stocksPrice ConcurrentMap to mutate
     * @param listStocks List of stocks to get prices from
     * @throws RuntimeException When HTTP error, JSON error, naming error
     */
    @Asynchronous
    public void fetchStockPrice(ConcurrentMap stocksPrice, List<String> listStocks) {
        final UrlBuild alpacaPriceUrl = new UrlBuild() {
            @Override
            public String buildUrl(String url, List<String> stocks, String feed) {
                String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
                dataUrl = dataUrl + "&feed=" + feed;
                return dataUrl;
            }
        };
        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            final String url = alpacaPriceUrl.buildUrl("https://data.alpaca.markets/v2/stocks/bars/latest",
                    listStocks, "iex");
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("APCA-API-KEY-ID", InitialContext.doLookup("alpacaAPIKey"))
                    .addHeader("APCA-API-SECRET-KEY", InitialContext.doLookup("alpacaSecretKey"))
                    .build();

            while (true) {
                updateStockPrice(stocksPrice, client, request);
                Thread.sleep(SLEEP_TIME);
            }
        }
        catch (IOException | InterruptedException | NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Using the prepared request gets most up-to-date stocks
     * and updates hashmap accordingly.
     * @param stocksPrice ConccurentMap of stock prices
     * @param client OkHttp Client
     * @param request Prepared OkHTTP request
     * @throws IOException throws exception for JSON errors
     */
    private static void updateStockPrice(ConcurrentMap stocksPrice,
                                         OkHttpClient client,
                                         Request request) throws IOException {
        System.out.println(Thread.currentThread());
        System.out.print("Async method price fetcher running");
        final Response response = client.newCall(request).execute();
        final JSONObject priceResponse =
                (new JSONObject(response.body().string())).getJSONObject("bars");

        response.body().close();

        for (String stock : priceResponse.keySet()) {
            stocksPrice.put(stock, priceResponse.getJSONObject(stock).getBigDecimal("vw"));
        }
    }
}

