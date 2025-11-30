package application.database;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import application.entities.OrderTicket;
import application.entities.Portfolio;
import application.use_case.Portfolio.PortfolioDBInterface;
import application.use_case.Price.PricesInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioDatabaseAccess implements PortfolioDBInterface {
    private static final long START_CASH = 10000000000L;
    private static final int USD_ADJUST = 100000;
    private static final int SQL_NON_UNIQUE = 19;
    private static final int USD_DECIMAL_SCALE = 5;
    private static final int INDEX1 = 1;
    private static final int INDEX2 = 2;
    private static final int INDEX3 = 3;

    // does database access have too much logic involved.
    // doesnt work with inject for some reason
    @Inject
    private PriceDatabaseAccess priceDb;

    // should i inject UserDB so i access get user id directly? or not injecting is low coupling
    private String url;

    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Add new user to portfolio DB cash table.
     * @param id user id
     * @throws RuntimeException exception
     */
    // should put this logic in interactor
    public void newUser(int id) {
        // 100000 USD. 100000 is 1 USD
        final long cash = START_CASH;

        final String sql = "INSERT INTO cash(user_id,USD) values(?,?)";
        // having connection open in singleton would prevent getting connection everytime
        // but sqllite doesnt have connection pooling
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setLong(2, cash);
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Price DB buy stock.
     * @param symbol stock
     * @param amount amount
     * @param id user_id
     * @return OrderTicket
     * @throws RuntimeException exception
     */
    @Override
    public OrderTicket buyStock(String symbol, int amount, int id) {
        long cash = 0;
        final BigDecimal cost;
        final String sql = "SELECT USD FROM cash WHERE user_id = ?";
        final String uSymbol = String.valueOf(id) + "\\" + symbol;
        long price = 0;
        long totalPrice = 0;
        final BigDecimal usdAdjust = new BigDecimal(USD_ADJUST);

        try (var conn = DriverManager.getConnection(url)) {
            final var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            final var result = stmt.executeQuery();

            if (result.next()) {
                cash = result.getLong("USD");
            }
            cost = priceDb.checkPrice(new PricesInput(symbol)).get(0).getPrice();

            price = cost.movePointRight(USD_DECIMAL_SCALE)
                    .setScale(0, BigDecimal.ROUND_CEILING).intValue();
            totalPrice = price * amount;

            if (cash < totalPrice) {
                return null;
            }

            result.close();
            stmt.close();

            cash -= totalPrice;
            updateCash(id, conn, cash);

            updateHoldings(amount, id, conn, uSymbol);
        }
        catch (SQLException ex) {
            // have to make sure this error code only happens when insert into holdings
            symbolAlreadyExists(amount, ex, uSymbol);
        }
        // this exception catches if checkPrice doesnt work.
        return new OrderTicket('b', symbol, amount, (new BigDecimal(price)).divide(usdAdjust),
                (new BigDecimal(totalPrice)).divide(usdAdjust));
    }

    /**
     * Update holdings after user bought.
     * @param amount amount
     * @param id id
     * @param conn connection
     * @param uSymbol symbol
     * @throws SQLException exception
     */
    private static void updateHoldings(int amount, int id, Connection conn, String uSymbol) throws SQLException {
        final String sql;
        final java.sql.PreparedStatement stmt;
        sql = "INSERT INTO holdings(user_id,symbol,holdings) values(?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(INDEX1, id);
        stmt.setString(INDEX2, uSymbol);
        stmt.setInt(INDEX3, amount);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Update Cash after user buys stock.
     * @param id user_id
     * @param conn connection
     * @param cash cash
     * @throws SQLException exception
     */
    private static void updateCash(int id, Connection conn, long cash) throws SQLException {
        final String sql;
        final java.sql.PreparedStatement stmt;
        sql = "UPDATE cash SET USD = ?"
                + " WHERE id = ?";

        stmt = conn.prepareStatement(sql);
        stmt.setLong(INDEX1, cash);
        stmt.setInt(INDEX2, id);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * For handling case when symbol already exists. User owns stock.
     *
     * @param amount  amount
     * @param exception exception
     * @param uSymbol symbol
     * @throws RuntimeException exception
     */
    private void symbolAlreadyExists(int amount, SQLException exception, String uSymbol) {
        final String sql;
        if (exception.getErrorCode() == SQL_NON_UNIQUE) {
            // automatically closes
            sql = "UPDATE holdings SET holdings = holdings + ?"
                    + " WHERE symbol = ?";
            try (var conn = DriverManager.getConnection(url);
                 var stmt2 = conn.prepareStatement(sql)) {
                stmt2.setInt(1, amount);
                stmt2.setString(2, uSymbol);
                stmt2.executeUpdate();
            }
            catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        else {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Sell stock Port DB.
     * @param symbol stock
     * @param amount amount
     * @param id user_id
     * @return OrderTicket
     * @throws RuntimeException exception
     */
    @Override
    public OrderTicket sellStock(String symbol, int amount, int id) {
        final BigDecimal cost;
        final String sql = "SELECT holdings FROM holdings WHERE symbol = ?";
        final String uSymbol = String.valueOf(id) + "\\" + symbol;
        int holdAmount = 0;
        long price = 0;
        long totalPrice = 0;
        final BigDecimal usdAdjust = new BigDecimal(100000);

        try (var conn = DriverManager.getConnection(url)) {
            final var stmt = conn.prepareStatement(sql);
            stmt.setString(1, uSymbol);
            final var result = stmt.executeQuery();

            // symbol is unique so only need to do it once
            if (result.next()) {
                holdAmount = result.getInt("holdings");
            }
            if (holdAmount < amount) {
                return null;
            }

            cost = priceDb.checkPrice(new PricesInput(symbol)).get(0).getPrice();
            price = cost.movePointRight(USD_DECIMAL_SCALE).setScale(0,
                    BigDecimal.ROUND_CEILING).intValue();
            totalPrice = price * amount;

            result.close();
            stmt.close();
            holdAmount -= amount;

            updateCash(id, conn, totalPrice);

            update(conn, holdAmount, uSymbol);

        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return new OrderTicket('s', symbol, amount,
                (new BigDecimal(price)).divide(usdAdjust),
                (new BigDecimal(totalPrice)).divide(usdAdjust));
    }

    /**
     * Updates holdings when user sells.
     * @param conn connection
     * @param holdAmount holdAmount
     * @param uSymbol symbol
     * @throws SQLException exception
     */
    private static void update(Connection conn, int holdAmount, String uSymbol) throws SQLException {
        final String sql;
        final java.sql.PreparedStatement stmt;
        sql = "UPDATE holdings SET holdings = ?"
                + " WHERE symbol = ?";

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, holdAmount);
        stmt.setString(2, uSymbol);
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public Portfolio getPortfolio(int user_id) {
        String sql = "SELECT usd FROM cash WHERE user_id = ?";
        long cash = 0;
        long value = 0;
        final Map<String, Integer> holdings = new HashMap<>();

        try (var conn = DriverManager.getConnection(url)) {
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                cash = result.getLong("USD");
                value += cash;
            }
            result.close();
            stmt.close();

            sql = "SELECT symbol,holdings FROM holdings WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(INDEX1, user_id);
            result = stmt.executeQuery();
            while (result.next()) {
                final String symbol = result.getString(INDEX1).split("\\\\")[1];
                final int amount = result.getInt(INDEX2);

                final BigDecimal cost = priceDb.checkPrice(new PricesInput(symbol)).get(0).getPrice();
                final long price = cost.movePointRight(USD_DECIMAL_SCALE)
                        .setScale(0, BigDecimal.ROUND_CEILING).intValue();
                value += price * amount;

                holdings.put(symbol, amount);
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        final BigDecimal performance = new BigDecimal(value - START_CASH)
                .divide(new BigDecimal(START_CASH), 2, RoundingMode.CEILING);

        return new Portfolio(cash, holdings, value, performance);
    }
}
