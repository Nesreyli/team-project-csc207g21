package application.database;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.naming.InitialContext;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class StockPriceDBFetcher {

    @Asynchronous
    public void fetchStockPrice(ConcurrentMap stocksPrice, List<String> listStocks){
        UrlBuild alpacaPriceUrl = new UrlBuild() {
            @Override
            public String buildUrl(String url, List<String> stocks, String feed) {
                String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
                dataUrl = dataUrl + "&feed=" + feed;
                return dataUrl;
            }
        };
        try {
            OkHttpClient client = new OkHttpClient.Builder().build();
            String url = alpacaPriceUrl.buildUrl("https://data.alpaca.markets/v2/stocks/bars/latest",
                    listStocks, "iex");
            Request request = new Request.Builder().
                    url(url).
                    get().
                    addHeader("accept", "application/json").
                    addHeader("APCA-API-KEY-ID", InitialContext.doLookup("alpacaAPIKey")).
                    addHeader("APCA-API-SECRET-KEY", InitialContext.doLookup("alpacaSecretKey")).
                    build();
            //throw exception when there is no matching stock which should not happend here cause
            //i already used the symbol in map to call it.
            // needs to be efficient
            // mabye dont use while true
            while (true) {
                System.out.println(Thread.currentThread());
                System.out.print("Async method price fetcher running");
                Response response = client.newCall(request).execute();
                JSONObject priceResponse = (new JSONObject(response.body().string())).getJSONObject("bars");
                response.body().close();
                // might just use this map as database if it faster
                // Map<String, Object> stocks = priceResponse.toMap();
                for (String stock : priceResponse.keySet()) {
//                    BigDecimal price = (BigDecimal) ((HashMap)stocks.get(stock)).get("vw");
//                    setStocksPrice(stock, price.movePointRight(2).intValueExact());
                    stocksPrice.put(stock, priceResponse.getJSONObject(stock).getBigDecimal("vw"));
                }
                //every 10 second should be fine honestly
                Thread.sleep(20000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

