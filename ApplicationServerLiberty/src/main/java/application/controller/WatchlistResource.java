package application.controller;

import application.use_case.watchlist.OutputWatchlist;
import application.use_case.watchlist.WatchlistInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/watchlist")
public class WatchlistResource {

    @Inject
    private WatchlistInteractor watchlistInteractor;

    /**
     * Endpoint for getting watchlist.
     * @param username username
     * @param password password
     * @return JSON
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist getWatchlist(@QueryParam("username") String username,
                                        @QueryParam("password") String password) {
        return watchlistInteractor.executeWatchlist(username, password, true);
    }

    /**
     * Endpoint for adding watchlist.
     * @param username username
     * @param password password
     * @param symbol symbol
     * @return JSON
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist addToWatchlist(@QueryParam("username") String username,
                                          @QueryParam("password") String password,
                                          @QueryParam("symbol") String symbol) {
        return watchlistInteractor.addStock(username, password, symbol);
    }

    /**
     * Endpoint for removing watchlist.
     * @param username username
     * @param password password
     * @param symbol symbol
     * @return JSON
     */
    @DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputWatchlist removeFromWatchlist(@QueryParam("username") String username,
                                               @QueryParam("password") String password,
                                               @QueryParam("symbol") String symbol) {
        return watchlistInteractor.removeStock(username, password, symbol);
    }
}
