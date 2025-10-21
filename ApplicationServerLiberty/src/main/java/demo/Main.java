package demo;


import okhttp3.*;
import jakarta.Database.UrlBuild;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void createUserDb(){
        String url = "jdbc:sqlite:portfolios.db";
        String sql = "CREATE TABLE IF NOT EXISTS user_record ("
                + " user_id integer PRIMARY KEY,"
                + " username text NOT NULL UNIQUE,"
                + " password text NOT NULL"
                + ");";

        try(var conn = DriverManager.getConnection(url); var stmt = conn.createStatement()){
            stmt.execute(sql);
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createPortfolioDb(){
        String url = "jdbc:sqlite:portfolios.db";
        String sql = "CREATE TABLE IF NOT EXISTS portfolio ("
                + " id integer PRIMARY KEY,"
                + " user_id integer FOREIGN KEY"
                + " symbol text"
                + " holdings integer"
                + " cash integer NOT NULL"
                + ");";

        try(var conn = DriverManager.getConnection(url); var stmt = conn.createStatement()){
            if(conn != null){
                 System.out.println(conn.getMetaData());
            }
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createStocksDb(){
        String url = "jdbc:sqlite:portfolios.db";
        String sql = "CREATE TABLE IF NOT EXISTS stocks_list ("
                + " name text NOT NULL,"
                + " symbol text NOT NULL"
                + ");";

        try(var conn = DriverManager.getConnection(url); var stmt = conn.createStatement()){
            stmt.execute(sql);
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void addStocks(String stockSymbol, String stockName){
        String url = "jdbc:sqlite:stocksList.db";
        String sql = "INSERT INTO stocks_list(name,symbol) VALUES(?,?)";
        try(var conn = DriverManager.getConnection(url); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, stockSymbol);
            stmt.setString(2, stockName);
            stmt.executeUpdate();
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addUser(String username, String password){
        String url = "jdbc:sqlite:userRecord.db";
        String sql = "INSERT INTO user_record(username,password) VALUES(?,?)"; //String.format(" VALUES(%s,%s)", username, password);
        try(var conn = DriverManager.getConnection(url); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            if(conn != null) {
                System.out.println(conn.getMetaData());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Pre-condition: List stocks has at least one stock
    public static String urlBuilder(String url, List<String> stocks, String feed){
        String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
        dataUrl = String.format(dataUrl + "&feed=%s", feed);
        return dataUrl;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        List<String> stocks = new ArrayList<>(Arrays.asList(new String[]{"AAPL,TSLA"}));
//        String url = urlBuilder("https://data.alpaca.markets/v2/stocks/quotes/latest", stocks, "iex");
//
//        Request request = new Request.Builder().
//                url(url).
//                get().
//                addHeader("accept", "application/json").
//                addHeader("APCA-API-KEY-ID", "PKFJL6BBXG8PNHL422KU").
//                addHeader("APCA-API-SECRET-KEY", "0xp8EGJfABZpESZnnKOoR8aX9E6gnF5PRggPvtFB").
//                build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
//        //createUserDb();
//        //addUser("Alex", "THTSP500");
//
//        createStocksDb();
//        BufferedReader stocksData = new BufferedReader(new FileReader("CompaniesMarketCap.com.csv"));
//        String headline = stocksData.readLine();
//        int line = 0;
//        while (line < 200){
//            String[] company = stocksData.readLine().split(",");
//            if(company[5].equals("United States")){
//                addStocks(company[2], company[1]);
//                line ++;
//            }
//        }
//    }
        UrlBuild alpacaPriceUrl = new UrlBuild() {
            @Override
            public String buildUrl(String url, List<String> stocks, String feed) {
                String dataUrl = url + "?symbols=" + String.join("%2C", stocks);
                // dataUrl = String.format(dataUrl + "&feed=%s", feed);
                dataUrl = dataUrl + "&feed=" + feed;
                return dataUrl;
            }
        };
        String url = alpacaPriceUrl.buildUrl("https://data.alpaca.markets/v2/stocks/bars/latest",
                new ArrayList<String>(List.of(new String[]{"AAPL", "MMM"})),"iex");
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder().
                    url("https://data.alpaca.markets/v2/stocks/bars/latest?symbols=AAPL%2CABBV%2CABNB%2CABT%2CADBE%2CADI%2CADP%2CADSK%2CAEP%2CAFL%2CAJG%2CALNY%2CAMAT%2CAMD%2CAMGN%2CAMT%2CAMZN%2CANET%2CAPD%2CAPH%2CAPO%2CAPP%2CAVGO%2CAXP%2CAZO%2CBA%2CBAC%2CBK%2CBKNG%2CBLK%2CBMY%2CBRK.B%2CBSX%2CBX%2CC%2CCAT%2CCDNS%2CCEG%2CCI%2CCL%2CCMCSA%2CCME%2CCMG%2CCMI%2CCOF%2CCOIN%2CCOP%2CCOR%2CCOST%2CCPNG%2CCRM%2CCRWD%2CCRWV%2CCSCO%2CCSX%2CCTAS%2CCVS%2CCVX%2CDASH%2CDDOG%2CDE%2CDELL%2CDHR%2CDIS%2CDLR%2CDUK%2CECL%2CELV%2CEMR%2CEOG%2CEPD%2CEQIX%2CET%2CFCX%2CFDX%2CFI%2CFTNT%2CGBTC%2CGD%2CGE%2CGEV%2CGILD%2CGLW%2CGM%2CGOOG%2CGS%2CHCA%2CHD%2CHLT%2CHON%2CHOOD%2CHWM%2CIBKR%2CIBM%2CICE%2CINTC%2CINTU%2CISRG%2CITW%2CJNJ%2CJPM%2CKKR%2CKLAC%2CKMI%2CKO%2CLHX%2CLLY%2CLMT%2CLOW%2CLRCX%2CMA%2CMAR%2CMCD%2CMCK%2CMCO%2CMDLZ%2CMET%2CMETA%2CMMC%2CMMM%2CMNST%2CMO%2CMPC%2CMRK%2CMRVL%2CMS%2CMSFT%2CMSI%2CMSTR%2CMU%2CNEE%2CNEM%2CNET%2CNFLX%2CNKE%2CNOC%2CNOW%2CNSC%2CNVDA%2CO%2CORCL%2CORLY%2CPANW%2CPEP%2CPFE%2CPG%2CPGR%2CPH%2CPLD%2CPLTR%2CPM%2CPNC%2CPWR%2CPYPL%2CQCOM%2CRBLX%2CRCL%2CREGN%2CROP%2CRSG%2CRTX%2CSBUX%2CSCCO%2CSCHW%2CSHW%2CSNOW%2CSNPS%2CSO%2CSPG%2CSPGI%2CSRE%2CSYK%2CT%2CTDG%2CTFC%2CTJX%2CTMO%2CTMUS%2CTRV%2CTSLA%2CTXN%2CUBER%2CUNH%2CUNP%2CUPS%2CURI%2CUSB%2CV%2CVRT%2CVRTX%2CVST%2CVZ%2CWDAY%2CWELL%2CWFC%2CWM%2CWMB%2CWMT%2CXOM%2CZTS&feed=iex").
                    get().
                    addHeader("accept", "application/json").
                    addHeader("APCA-API-KEY-ID", "PKFJL6BBXG8PNHL422KU").
                    addHeader("APCA-API-SECRET-KEY", "0xp8EGJfABZpESZnnKOoR8aX9E6gnF5PRggPvtFB").
                    build();
            //throw exception when there is no matching stock which should not happend here cause
            //i already used the symbol in map to call it.
        while(true){
            System.out.println("Async method running in: " +
                    Thread.currentThread().getName());
            Response response = client.newCall(request).execute();
            JSONObject priceResponse = (new JSONObject(response.body().string())).getJSONObject("bars");
            // might just use this map as database if it faster
            // Map<String, Object> stocks = priceResponse.toMap();
            for(String stock: priceResponse.keySet()){
//                    BigDecimal price = (BigDecimal) ((HashMap)stocks.get(stock)).get("vw");
//                    setStocksPrice(stock, price.movePointRight(2).intValueExact());
                System.out.println(priceResponse.getJSONObject(stock).getBigDecimal("vw"));
            }
            Thread.sleep(5000);
        }
//        String url = "jdbc:sqlite:/Users/myounghoonkim/Programming/jakartaee-hello-world/portfolios.db";
//        String sql = "SELECT symbol FROM stocks_list";
//        try(Connection conn = DriverManager.getConnection(url);
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql)){
//            if(conn != null) {
//                System.out.println(conn.getMetaData());
//            }
//            int i = 0;
//            while(rs.next() || i < 200){
//                System.out.println(rs.getString(1));
//                i++;
//            }
//        }catch(SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }
    }