package application.controller;

import application.use_case.Stock_Search.OutputDataSearch;
import application.use_case.Stock_Search.SearchStockInput;
import application.use_case.Stock_Search.SearchStockInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/stocks")
public class StockSearchResource {

    @Inject
    private SearchStockInteractor searchInteractor;

    /**
     * End point for search stock.
     * @param query search query
     * @return Output automatically binds to JSON
     */
    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataSearch searchStocks(@DefaultValue("")@QueryParam("query") String query) {
        final SearchStockInput searchInput = new SearchStockInput(query);
        return searchInteractor.executeSearch(searchInput);
    }

    /**
     * End point for getting a list of stock.
     * @return Output automatically binds to JSON
     */
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataSearch getAllStocks() {
        return searchInteractor.executeLoadAll();
    }
}
