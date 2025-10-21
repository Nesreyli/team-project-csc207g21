package jakarta.Database;


import jakarta.Entities.Price;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.UseCases.StockDatabaseInterface;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//I just have to inject database of stock which will be stored in a map.
// Should accessor be singleton supports concurrency. Multiple databse access.
// might have two seperate access for each priceDB orderDB
// implement if stock symbol doesnt exist

@Startup
@Singleton
public class StockDatabaseAccess implements StockDatabaseInterface {
    @Inject
    Price stockPrice;
    @Inject
    StockPriceDBFetcher self;

    // problem was i had this in StockPriceDBFetcher so even if it was concurrent it was created in the asynchronous thread
    // therefore everything was null. So if i want to access concurrent map i need to first initialize it in main thread
    // idk something with injection and concurrent hashmap
    private ConcurrentMap<String, BigDecimal> stocksPrice = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        String url;
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        String sql = "SELECT symbol FROM stocks_list";
        List<String> stocks = new ArrayList<>(200);
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
            int i = 0;
            while(rs.next() && i < 200){
                // the problem with concurrent map was putting null
                stocksPrice.put(rs.getString(1), new BigDecimal(-1));
                stocks.add(i, rs.getString(1));
                i++;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        self.fetchStockPrice(stocksPrice, stocks);
    }

    // might have been a problem where i calling new Price which container didnt see so problem with
    // async method
    @Override
    public Price checkPrice(String symbol) throws RuntimeException{

        // dont know why changing getting map from singleton asynchronous works
        // should i create new exception
        if (!stocksPrice.containsKey(symbol)){
            throw new RuntimeException("Illegal symbol");
        }
        // throws if Illegal price exception if price is negative
        stockPrice.setSymbol(symbol);
        stocksPrice.get(symbol);

        stockPrice.setPrice(stocksPrice.get(symbol));

        // there could be problem here with hashmap not being concurrent. as db updates it.
        //not thread safe
//        stockPrice.setPrice(stocksPriceDB.stocksPrice.get(symbol));
//        System.out.println(stocksPriceDB.stocksPrice.get(symbol));
        return stockPrice;
    }

    @Override
    public Price checkOrder(String symbol) {
        return null;
    }
}
