package Application.Database;


import Application.Entities.Price;
import Application.UseCases.Price.PricesInput;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import Application.UseCases.Price.StockDatabaseInterface;

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

@ApplicationScoped
public class PriceDatabaseAccess implements StockDatabaseInterface {
    // might have problem with injecting request scope
//    @Inject
//    Price stockPrice;
    @Inject
    StockPriceDBFetcher self;

    // problem was i had this in StockPriceDBFetcher so even if it was concurrent it was created in the asynchronous thread
    // therefore everything was null. So if i want to access concurrent map i need to first initialize it in main thread
    // idk something with injection and concurrent hashmap
    private ConcurrentMap<String, BigDecimal> stocksPriceDB = new ConcurrentHashMap<>();

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
                stocksPriceDB.put(rs.getString(1), new BigDecimal(-1));
                stocks.add(i, rs.getString(1));
                i++;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        self.fetchStockPrice(stocksPriceDB, stocks);
    }

    // might have been a problem where i calling new Price which container didnt see so problem with
    // async method
    @Override
    public ArrayList<Price> checkPrice(PricesInput symbols) throws RuntimeException{

        // dont know why changing getting map from singleton asynchronous works
        // should i create new exception
        int length = symbols.getSymbols().length;
        ArrayList<Price> prices = new ArrayList<>(length);
        for(int i = 0; i < length; i++){

            // might not be a good idea
            String[] s = symbols.getSymbols();

            if (!stocksPriceDB.containsKey(s[i])){
                throw new RuntimeException("Illegal symbol");
            }
            // i dont like how i creates new price for every request
            Price stockPrice = new Price();
            // throws if Illegal price exception if price is negative
            stockPrice.setSymbol(s[i]);
            stocksPriceDB.get(s[i]);

            stockPrice.setPrice(stocksPriceDB.get(s[i]));

            prices.add(i, stockPrice);
        }
        return prices;
    }

    @Override
    public Price checkOrder(PricesInput symbols) {
        return null;
    }
}
