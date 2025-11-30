package application.controller;

import application.use_case.Portfolio.OutputPortfolio;
import application.use_case.Portfolio.PortfolioInteractor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/portfolio")
public class PortfolioResource {
    @Inject
    PortfolioInteractor portInt;
    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON})
    public OutputPortfolio getPortfolio(@DefaultValue("") @QueryParam("username") String username,
                                        @DefaultValue("") @QueryParam("password") String password){
         return portInt.executePortfolio(username, password);
    }

}
