package DataAccess;

import Entity.Stock_Search;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchAccessObject {
    private static final String url = "http://100.67.15.150:4848/rest";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final int SUCCESS_CODE = 200;

    public HashMap<String, Stock_Search> searchStocks(String query){
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(url + "/stocks/search?query=%s", query))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try{
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt("message") == SUCCESS_CODE){
                return parseStocksFromResponse(responseBody);
            } else if (responseBody.isEmpty()) {
                throw new RuntimeException("No Stocks Found");
            }
            else{
                throw new RuntimeException("Search failed with status: " + responseBody.getInt("message"));
            }
        } catch (IOException ex){
            throw new RuntimeException("Network error occurred during search", ex);
        }
    }

    public HashMap<String, Stock_Search> getAllStocks(){
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(url + "/stock/list")
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try{
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt("message") == SUCCESS_CODE){
                return parseStocksFromResponse(responseBody);
            } else{
                throw new RuntimeException("Load all failed with status: " + responseBody.getInt("message"));
            }
        } catch (IOException ex){
            throw new RuntimeException("Network error occurred during search", ex);
        }
    }

    private HashMap<String, Stock_Search> parseStocksFromResponse(final JSONObject responseBody){
        HashMap<String, Stock_Search> stocks = new HashMap<>();
        JSONArray stocksArray = responseBody.getJSONArray("stocks");

        for (int i = 0; i < stocksArray.length(); i++){
            JSONObject stockJSON = stocksArray.getJSONObject(i);

            String symbol = stockJSON.getString("symbol");
            String company = stockJSON.getString("company");
            //BigDecimal market_cap = new BigDecimal(stockJSON.getString("marketcap"));
            BigDecimal price = stockJSON.getBigDecimal("price");
            String country = stockJSON.getString("country");


            Stock_Search stock = new Stock_Search(symbol, company, price, country);

            stocks.put(symbol, stock);
        }

        return stocks;
    }

}
