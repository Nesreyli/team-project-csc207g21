package application.controller;

import application.use_case.Portfolio.OutputPortfolio;
import application.use_case.Portfolio.PortfolioInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/portfolio")
public class PortfolioResource {

    @Inject
    private PortfolioInteractor portInt;

    /**
     * Endpoint for getting user portfolio.
     * @param username username
     * @param password password
     * @return Output binds to JSON
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputPortfolio getPortfolio(@DefaultValue("") @QueryParam("username") String username,
                                        @DefaultValue("") @QueryParam("password") String password) {
        return portInt.executePortfolio(username, password);
    }
}
