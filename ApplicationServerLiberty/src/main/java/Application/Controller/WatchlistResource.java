package Application.Controller;

import Application.UseCases.watchlist.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/watchlist")
public class WatchlistResource {

    @Inject
    WatchlistInteractor watchlistInteractor;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist getWatchlist(@QueryParam("username") String username,
                                        @QueryParam("password") String password) {
        return watchlistInteractor.executeWatchlist(username, password, true);
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist addToWatchlist(@QueryParam("username") String username,
                                          @QueryParam("password") String password,
                                          @QueryParam("symbol") String symbol) {
        return watchlistInteractor.addStock(username, password, symbol);
    }

    @DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist removeFromWatchlist(@QueryParam("username") String username,
                                               @QueryParam("password") String password,
                                               @QueryParam("symbol") String symbol) {
        return watchlistInteractor.removeStock(username, password, symbol);
    }
}
