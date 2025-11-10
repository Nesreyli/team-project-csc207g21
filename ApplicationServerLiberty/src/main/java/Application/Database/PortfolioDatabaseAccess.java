package Application.Database;

import Application.Entities.OrderTicket;
import Application.Entities.Portfolio;
import Application.UseCases.Portfolio.PortfolioDBInterface;
import Application.UseCases.Price.PricesInput;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class PortfolioDatabaseAccess implements PortfolioDBInterface {
    // does database access have too much logic involved.
    // doesnt work with inject for some reason
    @Inject
    PriceDatabaseAccess priceDB;

    //should i inject UserDB so i access get user id directly? or not injecting is low coupling
    private String url;
    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init(){
        priceDB.init();
    }

    //should put this logic in interactor
    public void newUser(int id){
        // 100000 USD. 100000 is 1 USD
        long cash = 10000000000L;

        String sql = "INSERT INTO cash(user_id,USD) values(?,?)";
        //having connection open in singleton would prevent getting connection everytime
        //but sqllite doesnt have connection pooling
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setLong(2, cash);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderTicket buyStock(String symbol, int amount, int id) {
        long cash = 0;
        BigDecimal cost;
        String sql = "SELECT USD FROM cash WHERE user_id = ?";
        String uSymbol = String.valueOf(id) + "\\" + symbol;
        long price = 0;
        long totalPrice = 0;
        BigDecimal usdAdjust = new BigDecimal(100000);

        try(var conn = DriverManager.getConnection(url);
            ){
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            var result = stmt.executeQuery();

            if(result.next()){
                cash = result.getLong("USD");
            }
            cost = priceDB.checkPrice(new PricesInput(symbol)).get(0).getPrice();

            price = cost.movePointRight(5).setScale(0,BigDecimal.ROUND_CEILING).intValue();
            totalPrice = price * amount;

            if(cash < totalPrice){
                return null;
            }

            result.close();
            stmt.close();

            cash -= totalPrice;
            sql = "UPDATE cash SET USD = ?"
                    + " WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, cash);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            sql = "INSERT INTO holdings(user_id,symbol,holdings) values(?,?,?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, uSymbol);
            stmt.setInt(3, amount);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            // have to make sure this error code only happens when insert into holdings
            //
            if(e.getErrorCode() == 19){
                //automatically closes
                sql = "UPDATE holdings SET holdings = holdings + ?" +
                        " WHERE symbol = ?";
                try(var conn = DriverManager.getConnection(url);
                    var stmt2 = conn.prepareStatement(sql)){
                    stmt2.setInt(1, amount);
                    stmt2.setString(2, uSymbol);
                    stmt2.executeUpdate();
                }catch(SQLException e2){
                    throw new RuntimeException(e2.getMessage());
                }
            } else{
                throw new RuntimeException(e.getMessage());
            }
        }
        //this exception catches if checkPrice doesnt work.
        catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return new OrderTicket('b', symbol, amount,(new BigDecimal(price)).divide(usdAdjust),
                (new BigDecimal(totalPrice)).divide(usdAdjust));
    }

    @Override
    public OrderTicket sellStock(String symbol, int amount, int id) {
        BigDecimal cost;
        String sql = "SELECT holdings FROM holdings WHERE symbol = ?";
        String uSymbol = String.valueOf(id) + "\\" + symbol;
        int holdAmount = 0;
        long price = 0;
        long totalPrice = 0;
        BigDecimal usdAdjust = new BigDecimal(100000);

        try(var conn = DriverManager.getConnection(url)){
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, uSymbol);
            var result = stmt.executeQuery();

            //symbol is unique so only need to do it once
            if(result.next()){
                holdAmount = result.getInt("holdings");
            }
            if(holdAmount < amount){
                return null;
            }

            cost = priceDB.checkPrice(new PricesInput(symbol)).get(0).getPrice();
            price = cost.movePointRight(5).setScale(0,BigDecimal.ROUND_CEILING).intValue();
            totalPrice = price * amount;

            result.close();
            stmt.close();
            holdAmount -= amount;

            sql = "UPDATE cash SET USD = USD + ?"
                    + " WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, totalPrice);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            sql = "UPDATE holdings SET holdings = ?"
                    + " WHERE symbol = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, holdAmount);
            stmt.setString(2, uSymbol);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return new OrderTicket('s', symbol, amount,(new BigDecimal(price)).divide(usdAdjust),
                (new BigDecimal(totalPrice)).divide(usdAdjust));
    }

    public Portfolio getPortfolio(int user_id){
        String sql = "SELECT usd FROM cash WHERE user_id = ?";
        BigDecimal usdAdjust = new BigDecimal(100000);
        long cash = 0;
        long value = 0;
        Map<String, Integer> holdings = new HashMap<>();

        try(var conn = DriverManager.getConnection(url)){
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            if(result.next()){
                cash = result.getLong("USD");
                value += cash;
            }
            result.close();
            stmt.close();

            sql = "SELECT symbol,holdings FROM holdings WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            result = stmt.executeQuery();
            while(result.next()){
                String symbol = result.getString(1).split("\\\\")[1];
                int amount = result.getInt(2);

                BigDecimal cost = priceDB.checkPrice(new PricesInput(symbol)).get(0).getPrice();
                long price = cost.movePointRight(5).setScale(0,BigDecimal.ROUND_CEILING).intValue();
                value += price * amount;

                holdings.put(symbol, amount);
            }
        }catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return new Portfolio(cash, holdings, value);
    }
}


