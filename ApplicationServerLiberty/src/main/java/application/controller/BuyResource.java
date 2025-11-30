package application.controller;

import application.use_case.Buy.BuyInteractor;
import application.use_case.Buy.MarketBuyInput;
import application.use_case.Buy.OutputDataBuy;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/buy")
public class BuyResource {

    @Inject
    private BuyInteractor buyInteractor;

    @Inject
    private MarketBuyInput marketBuyInput;

    /**
     * Endpoint for buy.
     * @param symbol stock
     * @param amount amount
     * @param username username
     * @param password password
     * @return JSON
     */
    @Path("/marketorder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataBuy buyMarketPrice(@DefaultValue("") @QueryParam("symbol") String symbol,
                                        @DefaultValue("") @QueryParam("amount") String amount,
                                        @DefaultValue("") @QueryParam("username") String username,
                                        @DefaultValue("") @QueryParam("password") String password) {
        marketBuyInput.setSymbol(symbol);
        // should i handle if parse int is error later or now
        marketBuyInput.setAmount(amount);
        marketBuyInput.setUsername(username);
        marketBuyInput.setPassword(password);

        return buyInteractor.executeMarketBuy(marketBuyInput);
    }
}
