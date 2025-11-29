package Setup;

import Application.Database.WatchlistDatabaseAccess;
import Application.UseCases.watchlist.WatchlistEntry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

//sql lite does not support concurrency with multiple connection pool
//use this class only once. to start up the database.
public class PortfolioDBStartup {
    private static final String DBurl;
    private static final String companyUrl;

    static {
        //Put url
        DBurl = "jdbc:sqlite:ApplicationServerLiberty/portfolios.db";
        companyUrl = "ApplicationServerLiberty/CompaniesMarketCap.com.csv";
    }
    public static void createUserTable(){
        String sql = "CREATE TABLE IF NOT EXISTS user_record ("
                + " user_id integer PRIMARY KEY,"
                + " username text NOT NULL UNIQUE,"
                + " password text NOT NULL"
                + ");";

        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.createStatement()){
            stmt.execute(sql);
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createHoldingsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS holdings ("
                + " id integer PRIMARY KEY,"
                // have user id and symbol as 1\AAPL. user 1's AAPL
                + " user_id integer,"
                + " symbol text UNIQUE,"
                + " holdings integer"
                + ");";

        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.createStatement()){
            if(conn != null){
                System.out.println(conn.getMetaData());
            }
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //1 USD is 100000
    public static void createCashTable(){
        String sql = "CREATE TABLE IF NOT EXISTS cash ("
                + " id integer PRIMARY KEY,"
                + " user_id integer UNIQUE,"
                + " USD integer"
                + ");";

        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.createStatement()){
            if(conn != null){
                System.out.println(conn.getMetaData());
            }
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createWatchlistTable() {
        String sql = "CREATE TABLE IF NOT EXISTS watchlist ("
                + "id INTEGER PRIMARY KEY,"
                + "user_id INTEGER NOT NULL,"
                + "symbol TEXT NOT NULL,"
                + "UNIQUE(user_id, symbol)"
                + " );";
        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.createStatement()){
            if(conn != null){
                System.out.println(conn.getMetaData());
            }
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createStocksTable(){
        String sql = "CREATE TABLE IF NOT EXISTS stocks_list ("
                + " id INTEGER PRIMARY KEY,"
                + " symbol text NOT NULL UNIQUE,"
                + " name text NOT NULL"
                + ");";

        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.createStatement()){
            stmt.execute(sql);
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void addStocks(String stockSymbol, String stockName){
        String sql = "INSERT INTO stocks_list(symbol,name) VALUES(?,?)";
        try(var conn = DriverManager.getConnection(DBurl); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, stockSymbol);
            stmt.setString(2, stockName);
            stmt.executeUpdate();

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addToWatchlist(int userId, String symbol) {
        String sql = "INSERT OR IGNORE INTO watchlist(user_id, symbol) VALUES(?, ?)";
        try (var conn = DriverManager.getConnection(DBurl);
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, symbol);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static String urlBuilder(String url, List<String> stocks, String feed){
//        String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
//        dataUrl = String.format(dataUrl + "&feed=%s", feed);
//        return dataUrl;
//    }

    public static void main(String[] args) throws IOException {
        createStocksTable();
        createUserTable();
        createHoldingsTable();
        createCashTable();
        createWatchlistTable();

        BufferedReader stocksData = new BufferedReader(new FileReader(companyUrl));
        String headline = stocksData.readLine();
        int line = 0;
        while (line < 200){
            String[] company = stocksData.readLine().split(",");
            if(company[5].equals("United States")){
                addStocks(company[2], company[1]);
                line ++;
            }
        }
    }
}
