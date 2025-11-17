package Application.Database;

import Application.Entities.Price;
import Application.Entities.Stock;
import Application.UseCases.Price.PricesInput;
import Application.UseCases.Stock_Search.SearchStockDatabaseInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SearchStockDatabaseAccess implements SearchStockDatabaseInterface {

    @Inject
    PriceDatabaseAccess priceDB;

    private final String url;
    {
        try {
            url = InitialContext.doLookup("JDBCsqlPortfolio");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT symbol, name FROM stocks_list ORDER BY symbol LIMIT 200";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            // Build map of symbol -> name from database
            Map<String, String> symbolToName = new HashMap<>();
            List<String> symbols = new ArrayList<>();

            while (rs.next()) {
                String symbol = rs.getString("symbol");
                String name = rs.getString("name");
                symbols.add(symbol);
                symbolToName.put(symbol, name);
            }

            // Fetch live prices from your existing PriceDatabaseAccess
            if (!symbols.isEmpty()) {
                try {
                    // Get prices in batches to avoid overwhelming
                    int batchSize = 50;
                    for (int i = 0; i < symbols.size(); i += batchSize) {
                        int end = Math.min(i + batchSize, symbols.size());
                        List<String> batch = symbols.subList(i, end);

                        String symbolsStr = String.join(",", batch);
                        PricesInput priceInput = new PricesInput(symbolsStr);
                        ArrayList<Price> prices = priceDB.checkPrice(priceInput);

                        for (Price priceObj : prices) {
                            String symbol = priceObj.getSymbol();
                            String name = symbolToName.get(symbol);
                            BigDecimal price = priceObj.getPrice();

                            stocks.add(new Stock(
                                    symbol,
                                    name != null ? name : symbol,
                                    price,
                                    "United States"
                            ));
                        }
                    }
                } catch (RuntimeException e) {
                    System.err.println("Error fetching prices: " + e.getMessage());
                    // Return stocks without prices on error
                    for (String symbol : symbols) {
                        stocks.add(new Stock(
                                symbol,
                                symbolToName.get(symbol),
                                BigDecimal.ZERO,
                                "United States"
                        ));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return stocks;
    }

    @Override
    public List<Stock> searchStock(String query) {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT symbol, name FROM stocks_list WHERE " +
                "LOWER(symbol) LIKE ? OR LOWER(name) LIKE ? " +
                "ORDER BY symbol LIMIT 50";

        String searchPattern = "%" + query.toLowerCase() + "%";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            var rs = stmt.executeQuery();

            Map<String, String> symbolToName = new HashMap<>();
            List<String> symbols = new ArrayList<>();

            while (rs.next()) {
                String symbol = rs.getString("symbol");
                String name = rs.getString("name");
                symbols.add(symbol);
                symbolToName.put(symbol, name);
            }

            // Fetch real-time prices
            if (!symbols.isEmpty()) {
                try {
                    String symbolsStr = String.join(",", symbols);
                    PricesInput priceInput = new PricesInput(symbolsStr);
                    ArrayList<Price> prices = priceDB.checkPrice(priceInput);

                    for (Price priceObj : prices) {
                        String symbol = priceObj.getSymbol();
                        String name = symbolToName.get(symbol);
                        BigDecimal price = priceObj.getPrice();

                        stocks.add(new Stock(
                                symbol,
                                name != null ? name : symbol,
                                BigDecimal.valueOf(price.doubleValue()),
                                "United States"
                        ));
                    }
                } catch (RuntimeException e) {
                    System.err.println("Error fetching prices for search: " + e.getMessage());
                    // Return results without prices if price fetch fails
                    for (String symbol : symbols) {
                        stocks.add(new Stock(
                                symbol,
                                symbolToName.get(symbol),
                                BigDecimal.valueOf(0.0),
                                "United States"
                        ));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return stocks;
    }
}
