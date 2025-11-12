package Application.Database;

import Application.UseCases.User.UserDatabaseInterface;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
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
        try(var conn = DriverManager.getConnection(url); var stmt = conn.prepareStatement(sql)
        ){
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

    public Integer getUserID(String username, String password){
        String sql = "SELECT user_id,username FROM user_record WHERE username = ? AND password = ?";
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            var rs = stmt.executeQuery();
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
            if(rs.next()){
                return rs.getInt("user_id");
            }
            else{
                return null;
            }
        }catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        // return -1 or null or throw exception
    }

    public Map<Integer, String> getUserIDs(){
        HashMap<Integer, String> userIDs = new HashMap<>();
        String sql = "SELECT user_id,username FROM user_record";
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.createStatement()){
            var rs = stmt.executeQuery(sql);
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
            while(rs.next()){
                userIDs.put(rs.getInt(1), rs.getString(2));
            }
        }catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return userIDs;
    }
}
