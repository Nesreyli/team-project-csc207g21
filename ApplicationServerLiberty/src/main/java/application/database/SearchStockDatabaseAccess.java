package application.database;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import application.entities.Price;
import application.entities.Stock;
import application.use_case.Price.PricesInput;
import application.use_case.Stock_Search.SearchStockDatabaseInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchStockDatabaseAccess implements SearchStockDatabaseInterface {
    private static final int BATCH = 50;
    private static final String US = "United States";
    @Inject
    private PriceDatabaseAccess priceDb;

    private final String url;

    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        }
        catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets all stocks.
     * @return list of stocks
     */
    @Override
    public List<Stock> getAllStocks() {
        final List<Stock> stocks = new ArrayList<>();
        final String sql = "SELECT symbol, name FROM stocks_list ORDER BY symbol LIMIT 200";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            // Build map of symbol -> name from database
            final Map<String, String> symbolToName = new HashMap<>();
            final List<String> symbols = new ArrayList<>();

            while (rs.next()) {
                final String symbol = rs.getString("symbol");
                final String name = rs.getString("name");
                symbols.add(symbol);
                symbolToName.put(symbol, name);
            }

            // Fetch live prices from your existing PriceDatabaseAccess
            if (!symbols.isEmpty()) {
                // Get prices in batches to avoid overwhelming
                final int batchSize = BATCH;
                for (int i = 0; i < symbols.size(); i += batchSize) {
                    final int end = Math.min(i + batchSize, symbols.size());
                    final List<String> batch = symbols.subList(i, end);

                    final String symbolsStr = String.join(",", batch);
                    final PricesInput priceInput = new PricesInput(symbolsStr);
                    final ArrayList<Price> prices = priceDb.checkPrice(priceInput);

                    for (Price priceObj : prices) {
                        final String symbol = priceObj.getSymbol();
                        final String name = symbolToName.get(symbol);
                        final BigDecimal price = priceObj.getPrice();

                        stocks.add(new Stock(
                                symbol,
                                name != null ? name : symbol,
                                price,
                                US
                        ));
                    }
                }
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return stocks;
    }

    @Override
    public List<Stock> searchStock(String query) {
        final List<Stock> stocks = new ArrayList<>();
        final String sql = "SELECT symbol, name FROM stocks_list WHERE "
                + "LOWER(symbol) LIKE ? OR LOWER(name) LIKE ? "
                + "ORDER BY symbol LIMIT 50";

        final String searchPattern = "%" + query.toLowerCase() + "%";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            final var rs = stmt.executeQuery();

            final Map<String, String> symbolToName = new HashMap<>();
            final List<String> symbols = new ArrayList<>();

            while (rs.next()) {
                final String symbol = rs.getString("symbol");
                final String name = rs.getString("name");
                symbols.add(symbol);
                symbolToName.put(symbol, name);
            }

            // Fetch real-time prices
            if (!symbols.isEmpty()) {
                
                final String symbolsStr = String.join(",", symbols);
                final PricesInput priceInput = new PricesInput(symbolsStr);
                final ArrayList<Price> prices = priceDb.checkPrice(priceInput);

                for (Price priceObj : prices) {
                    final String symbol = priceObj.getSymbol();
                    final String name = symbolToName.get(symbol);
                    final BigDecimal price = priceObj.getPrice();

                    stocks.add(new Stock(
                            symbol,
                            name != null ? name : symbol,
                            price,
                            US
                    ));
                }
            }
        } 
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return stocks;
    }
}
