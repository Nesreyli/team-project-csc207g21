package jakarta.Database;

import jakarta.Entities.OrderTicket;
import jakarta.Entities.User;
import jakarta.UseCases.PortfolioDBInterface;
import jakarta.UseCases.PricesInput;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String sql = "SELECT USD FROM cash WHERE user_id = ?";
        long cash = 0;
        BigDecimal cost;
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql);
            ){
            stmt.setInt(1, id);
            var result = stmt.executeQuery();

            if(result.next()){
                cash = result.getLong("USD");
            }
        }catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        try{
            cost = priceDB.checkPrice(new PricesInput(symbol)).get(0).getPrice();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        long price = cost.movePointRight(5).setScale(0,BigDecimal.ROUND_CEILING).intValue();
        long totalPrice = price * amount;

        if(cash < totalPrice){
            return null;
        }
        System.out.println(cash);
        System.out.println(totalPrice);
        cash -= totalPrice;
        sql = "UPDATE cash SET USD = ?"
                + " WHERE id = ?";
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql);){
            stmt.setLong(1, cash);
            stmt.setInt(2, id);
            var result = stmt.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        sql = "INSERT INTO holdings(symbol,holdings) values(?,?)";
        String sql2 = "UPDATE holdings SET holdings = holdings + ?" +
                " WHERE symbol = ?";

        String uSymbol = String.valueOf(id) + "\\" + symbol;

        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, uSymbol);
            stmt.setInt(2, amount);
            System.out.println(stmt);

            stmt.executeUpdate();

        }catch(SQLException e){
            if(e.getErrorCode() == 19){
                try(var conn = DriverManager.getConnection(url);
                    var stmt2 = conn.prepareStatement(sql2)){
                    stmt2.setInt(1, amount);
                    stmt2.setString(2, uSymbol);
                    stmt2.executeUpdate();
                }catch(SQLException e2){
                    throw new RuntimeException(e2.getMessage());
                }
            }
            else{
                throw new RuntimeException(e.getMessage());
            }
        }
        BigDecimal usdAdjust = new BigDecimal(100000);
        return new OrderTicket('b', symbol, amount,(new BigDecimal(price)).divide(usdAdjust),
                (new BigDecimal(totalPrice)).divide(usdAdjust));
    }
}
