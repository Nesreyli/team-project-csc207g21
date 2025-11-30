package DataAccess;

import Entity.*;
import InterfaceAdapter.stock_price.PriceState;
import UseCase.buySell.BuySellAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class BuySellAccessObject implements BuySellAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String MESSAGE = "message";
    private final String url = "http://100.71.12.182:4848/rest";

    public Response setStockData(PriceState priceState, Integer amount, Boolean isBuy) {
        String symbol = priceState.getSymbol();
        String username =  priceState.getUsername();
        String password = priceState.getPassword();

        Character order;
        String outSymbol;
        Integer outAmount;
        BigDecimal price;
        BigDecimal totalPrice;

        String apiUrl;
        if (isBuy) {
            apiUrl = "buy/marketorder/?symbol=%s&amount=%s&username=%s&password=%s";
        } else {
            apiUrl = "sell/marketorder/?symbol=%s&amount=%s&username=%s&password=%s";
        }

        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + apiUrl,
                        symbol, amount, username, password))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            final JSONObject buyResponseBody;
            try (okhttp3.Response buyResponse = client.newCall(request).execute()) {
                assert buyResponse.body() != null;
                buyResponseBody = new JSONObject(buyResponse.body().string());
            }

            if (buyResponseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                order = buyResponseBody.getString("order").toCharArray()[0];
                outSymbol = buyResponseBody.getString("symbol");
                outAmount = buyResponseBody.getInt("amount");
                price = new BigDecimal(buyResponseBody.getString("price"));
                totalPrice = new BigDecimal(buyResponseBody.getString("totalPrice"));
            } else {
                // Error in buy request
                return ResponseFactory.create(buyResponseBody.getInt(MESSAGE), null);
            }

        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

        BuySellReceipt buySellReceipt = new BuySellReceipt.Builder().order(order)
                .amount(outAmount).symbol(outSymbol).price(price).totalPrice(totalPrice).build();

        return ResponseFactory.create(SUCCESS_CODE, buySellReceipt);
    }
}
