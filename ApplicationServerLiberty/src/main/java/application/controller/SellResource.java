package application.controller;

import application.use_case.Sell.MarketSellInput;
import application.use_case.Sell.OutputDataSell;
import application.use_case.Sell.SellInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/sell")
public class SellResource {
    @Inject
    private SellInteractor sellInteractor;

    @Inject
    private MarketSellInput marketSellInput;

    /**
     * End point for selling stocks.
     * @param symbol stock symbol
     * @param amount amount sell
     * @param username username
     * @param password password
     * @return Output automatically binds to JSON
     */
    @Path("/marketorder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OutputDataSell buyMarketPrice(@DefaultValue("") @QueryParam("symbol") String symbol,
                                         @DefaultValue("") @QueryParam("amount") String amount,
                                         @DefaultValue("") @QueryParam("username") String username,
                                         @DefaultValue("") @QueryParam("password") String password) {
        marketSellInput.setSymbol(symbol);
        marketSellInput.setAmount(amount);
        marketSellInput.setUsername(username);
        marketSellInput.setPassword(password);

        return sellInteractor.executeMarketSell(marketSellInput);
    }
}
