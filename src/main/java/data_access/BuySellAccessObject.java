package data_access;

import entity.*;
import interface_adapter.stock_price.PriceState;
import use_case.buySell.BuySellAccessInterface;
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
    private final String url = " http://localhost:4848/rest";


    /**
     * Sends a buy/sell order to backend then parses result
     * @param priceState the current price information needed to evaluate the order
     * @param amount     the number of shares to buy or sell
     * @param isBuy      true for a buy order, false for a sell order
     * @return containing either receipt or error message
     */
    public Response setStockData(PriceState priceState, Integer amount, Boolean isBuy) {
        final String symbol = priceState.getSymbol();
        final String username =  priceState.getUsername();
        final String password = priceState.getPassword();

        final Character order;
        final String outSymbol;
        final Integer outAmount;
        final BigDecimal price;
        final BigDecimal totalPrice;
        final String apiUrl;

        if (isBuy) {
            apiUrl = "/buy/marketorder/?symbol=%s&amount=%s&username=%s&password=%s";
        }
        else {
            apiUrl = "/sell/marketorder/?symbol=%s&amount=%s&username=%s&password=%s";
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
                price = buyResponseBody.getBigDecimal("price");
                totalPrice = buyResponseBody.getBigDecimal("totalPrice");
            }
            else {
                // Error in buy request
                return ResponseFactory.create(buyResponseBody.getInt(MESSAGE), null);
            }

        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

        BuySellReceipt buySellReceipt = new BuySellReceipt.Builder().order(order)
                .amount(outAmount).symbol(outSymbol).price(price).totalPrice(totalPrice).build();

        return ResponseFactory.create(SUCCESS_CODE, buySellReceipt);
    }
}
