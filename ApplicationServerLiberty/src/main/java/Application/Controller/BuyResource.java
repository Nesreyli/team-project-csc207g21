package jakarta.Controller;

import jakarta.UseCases.Buy.BuyInteractor;
import jakarta.UseCases.Buy.MarketBuyInput;
import jakarta.UseCases.Buy.OutputDataBuy;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/buy")
public class BuyResource {
    @Inject
    BuyInteractor buyInteractor;

    @Inject
    MarketBuyInput marketBuyInput;

    @Path("/marketorder")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataBuy buyMarketPrice(@DefaultValue("") @QueryParam("symbol") String symbol,
                                        @DefaultValue("") @QueryParam("amount") String amount,
                                        @DefaultValue("") @QueryParam("username") String username,
                                        @DefaultValue("") @QueryParam("password") String password){
        marketBuyInput.setSymbol(symbol);
        //should i handle if parse int is error later or now
        marketBuyInput.setAmount(amount);
        marketBuyInput.setUsername(username);
        marketBuyInput.setPassword(password);


        var a = buyInteractor.executeMarketBuy(marketBuyInput);
        return a;
    }
}
