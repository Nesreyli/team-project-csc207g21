package jakarta.Database;

import jakarta.UseCases.UserDatabaseInterface;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Startup
@Singleton
public class UserDatabaseAccess implements UserDatabaseInterface {
    private String url;
    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    // return void? also exception to handle business logic? idk also have to be aware from where in
    // stack do i return exception
    // throws exception
    // is entity really needed
    public boolean addUser(String username, String password){
        String sql = "INSERT INTO user_record(username,password) VALUES(?,?)";
        try(var conn = DriverManager.getConnection(url); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            return false;
        }
        return true;
    }
    public void verifyUser(String username, String password){
    }
}
