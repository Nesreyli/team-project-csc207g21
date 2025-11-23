package DataAccess;

import Entity.*;
import UseCase.stock.StockAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class StockAccessObject implements StockAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String MESSAGE = "message";
    private final String url = "http://100.71.12.182:4848/rest";

    public Response getStockData(String symbol) {

        BigDecimal price;
        BigDecimal performance;
        BigDecimal ytdPrice;

        // Get price data
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + "/stocks/price/?symbols=%s", symbol))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            final JSONObject priceResponseBody;
            try (okhttp3.Response priceResponse = client.newCall(request).execute()) {
                assert priceResponse.body() != null;
                priceResponseBody = new JSONObject(priceResponse.body().string());
            }

            if (priceResponseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                JSONObject priceObject = priceResponseBody
                        .getJSONObject("prices").getJSONObject(symbol);
                price = priceObject.getJSONObject(symbol).getBigDecimal("p");
                ytdPrice = priceObject.getJSONObject(symbol).getBigDecimal("ytd");
                performance = priceObject.getJSONObject(symbol).getBigDecimal("perf");
            } else {
                // Error in price request
                return ResponseFactory.create(priceResponseBody.getInt(MESSAGE), null);
            }
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

        Stock stockStock = new Stock.Builder().symbol(symbol)
                        .price(price).value(ytdPrice).performance(performance).build();

        return ResponseFactory.create(SUCCESS_CODE, stockStock);
    }
}
