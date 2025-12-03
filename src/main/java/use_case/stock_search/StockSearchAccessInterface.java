package use_case.stock_search;

import java.util.Map;
import entity.Stock_Search;

/**
 * The Access Interface for the Stock Search Use Case.
 * Defines the contract for data access operations related to stock searching.
 */
public interface StockSearchAccessInterface {

    /**
     * Searches for stocks whose symbol or company name matches the given query.
     *
     * @param query the search keyword entered by the user
     * @return a map of stock symbols to Stock_Search objects that match the query
     * @throws RuntimeException if the network request fails, no results are found,
     *                          or the API responds with an unexpected status code
     */
    Map<String, Stock_Search> searchStocks(String query);

    /**
     * Retrieves all available stocks from the backend API.
     *
     * @return a map of stock symbols to their Stock_Search objects
     * @throws RuntimeException if the network request fails or the API responds with
     *                          an unexpected status code
     */
    Map<String, Stock_Search> getAllStocks();
}