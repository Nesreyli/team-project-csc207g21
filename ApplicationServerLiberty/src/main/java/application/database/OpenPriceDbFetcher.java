package application.database;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONObject;

import jakarta.enterprise.context.ApplicationScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ApplicationScoped
public class OpenPriceDbFetcher {

    /**
     * Fetches open price of 2025 once at server start.
     * @param stocksPrice stocksPrice map to mutate
     * @param listStocks listStocks
     * @throws RuntimeException exception
     */
    public void fetchOpenPrice(Map stocksPrice, List<String> listStocks) {

        try {
            final OkHttpClient client = new OkHttpClient.Builder().build();
            final String url = "https://data.alpaca.markets/v2/stocks/bars?symbols="
                    + String.join("%2C", listStocks)
                    + "&timeframe=23H&start=2025-01-01T00%3A00%3A00Z&end=2025-01-02T00%3A01%3A00Z&"
                    + "limit=200&adjustment=all&feed=sip&sort=asc";

            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("APCA-API-KEY-ID", InitialContext.doLookup("alpacaAPIKey"))
                    .addHeader("APCA-API-SECRET-KEY", InitialContext.doLookup("alpacaSecretKey"))
                    .build();
            // throw exception when there is no matching stock which should not happend here cause
            // i already used the symbol in map to call it.
            // needs to be efficient
            // mabye dont use while true

            final Response response = client.newCall(request).execute();
            final JSONObject priceResponse = (new JSONObject(response.body().string()))
                    .getJSONObject("bars");
            response.body().close();
            // might just use this map as database if it faster
            // Map<String, Object> stocks = priceResponse.toMap();
            for (String stock : priceResponse.keySet()) {
                // BigDecimal price = (BigDecimal) ((HashMap)stocks.get(stock)).get("vw");
                // setStocksPrice(stock, price.movePointRight(2).intValueExact());
                stocksPrice.put(stock, priceResponse.getJSONArray(stock)
                        .getJSONObject(0).getBigDecimal("vw"));
            }
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
