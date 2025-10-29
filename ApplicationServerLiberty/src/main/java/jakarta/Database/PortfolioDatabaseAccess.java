package jakarta.Database;

import jakarta.UseCases.PortfolioDBInterface;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Startup
@Singleton
public class PortfolioDatabaseAccess implements PortfolioDBInterface {
    //should i inject UserDB so i access get user id directly? or not injecting is low coupling
    private String url;
    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(int id){
        // 100000 USD. 100000 is 1 USD
        long cash = 100000000000L;

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
}
