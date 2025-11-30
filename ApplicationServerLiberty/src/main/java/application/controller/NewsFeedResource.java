package application.controller;

import application.use_case.news.NewsFeedInteractor;
import application.use_case.news.OutputNews;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/news")
public class NewsFeedResource {

    @Inject
    private NewsFeedInteractor interactor;

    /**
     * Endpoint for news.
     * @return JSON
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public OutputNews getNews() {
        return interactor.execute();
    }
}
