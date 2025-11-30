package application.controller;

import application.use_case.Price.OutputDataPrice;
import application.use_case.Price.PricesInput;
import application.use_case.Price.getPriceInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

// later on implement where they can get all the price of stocks like an array by like having query seperate by #
// query param vs path param
@Path("/stocks")
public class PriceResource {

    @Inject
    private getPriceInteractor priceInteractor;

    @Inject
    private PricesInput priceInput;

    /**
     * Endpoint for getting stock price.
     * @param symbols symbol separated by %2C
     * @return Output binds to JSON
     */

    @Path("/price")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataPrice getPrice(@QueryParam("symbols") String symbols) {
        if (symbols == null) {
            priceInput.setSymbols("");
        }
        else {
            priceInput.setSymbols(symbols);
        }
        // interact through interface
        return priceInteractor.executePrice(priceInput);
    }
}
