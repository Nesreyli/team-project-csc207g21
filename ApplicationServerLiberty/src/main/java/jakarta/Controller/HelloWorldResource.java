package jakarta.Controller;

import jakarta.UseCases.OutputDataPrice;
import jakarta.UseCases.PricesInputData;
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
    //think there is a way to inject fields directly too
    // injecting request scoped
    @Inject
    PricesInputData priceInput;
    //symbol separated by %2C
    @Path("price/")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public OutputDataPrice getPrice(@QueryParam("symbols") String symbols) {
        if ((symbols == null) || symbols.trim().isEmpty()) {
        }
        System.out.println(symbols);
        priceInput.setSymbols(symbols);
        // interact through interface
        return priceInteractor.executePrice(priceInput);
    }
}
