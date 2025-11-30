package application.controller;
import application.use_case.news.NewsFeedInteractor;
import application.use_case.news.OutputNews;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/news")
public class NewsFeedResource {
    @Inject
    private NewsFeedInteractor interactor;

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON})
    public OutputNews getNews() {
        return interactor.execute();
    }
}
