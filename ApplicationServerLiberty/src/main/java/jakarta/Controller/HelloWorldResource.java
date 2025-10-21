package jakarta.Controller;

import jakarta.UseCases.OutputDataPrice;
import jakarta.UseCases.getPriceInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.Entities.Price;

// later on implement where they can get all the price of stocks like an array by like having query seperate by #
// query param vs path param
@Path("stocks/")
public class HelloWorldResource {
    @Inject
    getPriceInteractor priceInteractor;

    @Path("price/")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public OutputDataPrice getPrice(@QueryParam("symbol") String symbol) {
        if ((symbol == null) || symbol.trim().isEmpty()) {

        }
        return priceInteractor.executePrice(symbol);
    }
}
