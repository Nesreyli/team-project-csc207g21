package data_access;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Stock_Search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.stock_search.StockSearchAccessInterface;

public class SearchAccessObject implements StockSearchAccessInterface {
    private static final String url = "http://100.67.4.80:4848/rest";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final int SUCCESS_CODE = 200;

    /**
     * Searches for stocks whose symbol or company name matches the given query.
     *
     * @param query the search keyword entered by the user.
     * @return a map of stock symbols to Stock_Search objects that match the query.
     * @throws RuntimeException if the network request fails, no results are found,
     *                          or the API responds with an unexpected status code.
     */
    public Map<String, Stock_Search> searchStocks(String query) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + "/stocks/search?query=%s", query))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt("message") == SUCCESS_CODE) {
                return parseStocksFromResponse(responseBody);
            }
            else if (responseBody.isEmpty()) {
                throw new RuntimeException("No Stocks Found");
            }
            else {
                throw new RuntimeException("Search failed with status: " + responseBody.getInt("message"));
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Network error occurred during search", ex);
        }
    }

    /**
     * Retrieves all available stocks from the backend API.
     *
     * @return a map of stock symbols to their {@link Stock_Search} objects.
     * @throws RuntimeException if the network request fails or the API responds with
     *                          an unexpected status code.
     */
    public Map<String, Stock_Search> getAllStocks() {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(url + "/stocks/list")
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt("message") == SUCCESS_CODE) {
                return parseStocksFromResponse(responseBody);
            }
            else {
                throw new RuntimeException("Load all failed with status: " + responseBody.getInt("message"));
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Network error occurred during search", ex);
        }
    }

    private Map<String, Stock_Search> parseStocksFromResponse(final JSONObject responseBody) {
        final Map<String, Stock_Search> stocks = new HashMap<>();
        final JSONArray stocksArray = responseBody.getJSONArray("stocks");

        for (int i = 0; i < stocksArray.length(); i++) {
            final JSONObject stockJSON = stocksArray.getJSONObject(i);

            final String symbol = stockJSON.getString("symbol");
            final String company = stockJSON.getString("company");
            final BigDecimal price = stockJSON.getBigDecimal("price");
            final String country = stockJSON.getString("country");

            final Stock_Search stock = new Stock_Search(symbol, company, price, country);

            stocks.put(symbol, stock);
        }

        return stocks;
    }

}
