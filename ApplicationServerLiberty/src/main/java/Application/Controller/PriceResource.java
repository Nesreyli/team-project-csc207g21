package jakarta.Controller;

import jakarta.UseCases.Price.OutputDataPrice;
import jakarta.UseCases.Price.PricesInput;
import jakarta.UseCases.Price.getPriceInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

// later on implement where they can get all the price of stocks like an array by like having query seperate by #
// query param vs path param
@Path("/stocks")
public class PriceResource {
    @Inject
    getPriceInteractor priceInteractor;
    //think there is a way to inject fields directly too
    // injecting request scoped
    @Inject
    PricesInput priceInput;

    //symbol separated by %2C
    @Path("/price")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataPrice getPrice(@QueryParam("symbols") String symbols) {
        if (symbols == null) {
            symbols = "";
        }
        System.out.println(symbols);
        priceInput.setSymbols(symbols);
        // interact through interface
        return priceInteractor.executePrice(priceInput);
    }
}