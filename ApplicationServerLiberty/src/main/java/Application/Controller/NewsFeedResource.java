package Application.Controller;
import Application.UseCases.news.NewsFeedInteractor;
import Application.UseCases.news.OutputNews;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
