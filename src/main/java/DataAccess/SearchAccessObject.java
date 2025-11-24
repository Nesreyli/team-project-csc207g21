package DataAccess;

import Entity.Stock;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SearchAccessObject {
    private static final String url = "http://alex-mh-mbp:4848/rest";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final int SUCCESS_CODE = 200;

    public List<Stock> searchStocks(String query){
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
            } else{
                throw new RuntimeException("Search failed with status: " + responseBody.getInt("message"));
            }
        } catch (IOException ex){
            throw new RuntimeException("Network error occurred during search", ex);
        }
    }

    public List<Stock> getAllStocks(){
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

    private List<Stock> parseStocksFromResponse(final JSONObject responseBody){
        List<Stock> stocks = new ArrayList<>();
        JSONArray stocksArray = responseBody.getJSONArray("stocks");

        for (int i = 0; i < stocksArray.length(); i++){
            JSONObject stockJSON = stocksArray.getJSONObject(i);

            String symbol = stockJSON.getString("symbol");
            String company = stockJSON.getString("company");
            BigDecimal marketcap = new BigDecimal(stockJSON.getString("marketcap"));
            BigDecimal price = stockJSON.getBigDecimal("price");
            String country = stockJSON.getString("country");


            stocks.add(new Stock(symbol, company, price, marketcap, country));
        }
        return stocks;
    }
}
