package Application.Controller;


import Application.UseCases.Stock_Search.OutputDataSearch;
import Application.UseCases.Stock_Search.SearchStockInput;
import Application.UseCases.Stock_Search.SearchStockInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/stocks")
public class StockSearchResource {
    @Inject
    SearchStockInteractor searchInteractor;

    @Path("/search")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataSearch searchStocks(@DefaultValue("") @QueryParam("query") String query) {
        SearchStockInput searchInput = new SearchStockInput(query);
        return searchInteractor.executeSearch(searchInput);
    }

    @Path("/list")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataSearch getAllStocks() {
        return searchInteractor.executeLoadAll();
    }
}
