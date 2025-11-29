// Application/Controller/WatchlistResource.java
package Application.Controller;

import Application.UseCases.watchlist.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/watchlist")
public class WatchlistResource {

    @Inject
    WatchlistInteractor watchlistInteractor;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist getWatchlist(@PathParam("userId") int userId,
                                        @QueryParam("includePrices") boolean includePrices) {
        return watchlistInteractor.executeWatchlistByUserId(userId, includePrices);
    }

    @POST
    @Path("/{userId}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WatchlistEntry addToWatchlist(@PathParam("userId") int userId, WatchlistEntry entry) {
        return watchlistInteractor.addStockToWatchlist(userId, entry.getSymbol());
    }

    @DELETE
    @Path("/{userId}/remove/{symbol}")
    public void removeFromWatchlist(@PathParam("userId") int userId, @PathParam("symbol") String symbol) {
        watchlistInteractor.removeStockFromWatchlist(userId, symbol);
    }
}
