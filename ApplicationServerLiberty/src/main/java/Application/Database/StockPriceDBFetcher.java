package jakarta.Database;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.naming.InitialContext;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class StockPriceDBFetcher {
//    ConcurrentMap<String, BigDecimal> stocksPrice = new ConcurrentHashMap<>();
//
//    @Inject
//    StockPriceDBFetcher self;
//
//    @PostConstruct
//    public void init(){
//        String url = "url";
//        String sql = "SELECT symbol FROM stocks_list";
//        List<String> stocks = new ArrayList<>(200);
//        try(Connection conn = DriverManager.getConnection(url);
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql)){
//            if(conn != null) {
//                System.out.println(conn.getMetaData());
//            }
//            int i = 0;
//            while(rs.next() && i < 200){
//                // the problem with concurrent map was putting null
//                stocksPrice.put(rs.getString(1), new BigDecimal(-1));
//                stocks.add(i, rs.getString(1));
//                i++;
//            }
//        }catch(SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        self.fetchStockPrice(stocks);
//    }

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
                System.out.println(stocksPrice);
                System.out.print("Async method running");
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
            System.out.println(e.getMessage());
        }
    }
}

