package jakarta.Database;

import jakarta.Entities.OrderTicket;
import jakarta.Entities.User;
import jakarta.UseCases.PortfolioDBInterface;
import jakarta.UseCases.PricesInput;
import jakarta.annotation.PostConstruct;
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
        String uSymbol = uSymbol = String.valueOf(id) + "\\" + symbol;
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

            sql = "INSERT INTO holdings(symbol,holdings) values(?,?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uSymbol);
            stmt.setInt(2, amount);
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
}
