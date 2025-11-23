package DataAccess;

import Application.Entities.Stock;
import Entity.StockResponse;
import UseCase.stock.StockAccessInterface;
import Entity.Response;
import Entity.ResponseFactory;
import Entity.Portfolio;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class StockAccessObject extends PortfolioAccessObject implements StockAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String MESSAGE = "message";
    private final String url = "http://100.71.12.182:4848/rest";

    public Response getTransactionData(String username, String password, String symbol) {

        // Initialize variables
        Integer owned;
        BigDecimal value;
        BigDecimal price;
        String company = "";
        String country = "";

        // Get user data (stocks owned, balance)
        Response portData = getPort(username, password);
            if (portData.getStatus_code() ==  SUCCESS_CODE) {
                Portfolio portfolioObj = (Portfolio) portData.getEntity();

                Object holdingsObject = portfolioObj.getHoldings().get(symbol);
                if (holdingsObject == null) {
                    owned = 0;
                } else {
                    owned = (Integer) holdingsObject;
                }

                value = portfolioObj.getValue();

            } else {
                // Error in portfolio request
                return portData;
            }

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
                JSONObject priceObject = priceResponseBody.getJSONObject("price");
                price = priceObject.getJSONObject(symbol).getBigDecimal("p");
            } else {
                // Error in price request
                return ResponseFactory.create(priceResponseBody.getInt(MESSAGE), null);
            }
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

        // Get stock data
        final Request stockRequest = new Request.Builder()
                .url(String.format(url + "/stocks/search/?query=%s", symbol))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            final JSONObject stockResponseBody;
            try (okhttp3.Response stockResponse = client.newCall(stockRequest).execute()) {
                assert stockResponse.body() != null;
                stockResponseBody = new JSONObject(stockResponse.body().string());
            }

            if (stockResponseBody.getInt(MESSAGE) == SUCCESS_CODE) {
                boolean found = false;
                int i = 0;
                while (i < 10) {
                    JSONObject stockObject = stockResponseBody.getJSONArray("stock").getJSONObject(i);
                    if (stockObject.getString("symbol").equals(symbol)) {
                        company = stockObject.getString("company");
                        country = stockObject.getString("country");
                        break;
                    }
                    i++;
                }

                if (!found) {
                    return ResponseFactory.create(stockResponseBody.getInt(MESSAGE), null);
                }

            } else {
                return ResponseFactory.create(stockResponseBody.getInt(MESSAGE), null);
            }

        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }

        StockResponse stockResponse = new StockResponse(owned, value, price, company, country);

        return ResponseFactory.create(SUCCESS_CODE, stockResponse);
    }
}
