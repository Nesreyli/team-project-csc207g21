package org.eclipse.jakarta.hello;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

//sql lite does not support concurrency with multiple connection pool
//use this class only once. to start up the database.
public class StockListDBStartup {
    private static final String DBurl;
    private static final String companyUrl;

    static {
        DBurl = "jdbc:sqlite:/Users/myounghoonkim/Programming/Projects/207/team-project-csc207g21/ApplicationServerLiberty/portfolios.db";
        companyUrl = "/Users/myounghoonkim/Programming/Projects/207/team-project-csc207g21/ApplicationServerLiberty/CompaniesMarketCap.com.csv";
    }

    public static void createStocksDb(){
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

//    public static String urlBuilder(String url, List<String> stocks, String feed){
//        String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
//        dataUrl = String.format(dataUrl + "&feed=%s", feed);
//        return dataUrl;
//    }

    public static void main(String[] args) throws IOException {
        createStocksDb();
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
