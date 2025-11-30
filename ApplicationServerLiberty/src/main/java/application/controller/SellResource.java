package application.controller;

import application.use_case.Sell.MarketSellInput;
import application.use_case.Sell.OutputDataSell;
import application.use_case.Sell.SellInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/sell")
public class SellResource {
    @Inject
    SellInteractor sellInteractor;

    @Inject
    MarketSellInput marketSellInput;

    @Path("/marketorder")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public OutputDataSell buyMarketPrice(@DefaultValue("") @QueryParam("symbol") String symbol,
                                         @DefaultValue("") @QueryParam("amount") String amount,
                                         @DefaultValue("") @QueryParam("username") String username,
                                         @DefaultValue("") @QueryParam("password") String password){
        marketSellInput.setSymbol(symbol);
        //should i handle if parse int is error later or now
        marketSellInput.setAmount(amount);
        marketSellInput.setUsername(username);
        marketSellInput.setPassword(password);


        return sellInteractor.executeMarketSell(marketSellInput);
    }
}
