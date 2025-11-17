package Application.Controller;
import Application.UseCases.News.NewsFeedInteractor;
import Application.UseCases.News.OutputNews;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/portfolio")
public class NewsFeedResource {
    @Inject
    private NewsFeedInteractor interactor;

    public NewsFeedResource(NewsFeedInteractor interactor) {
        this.interactor = interactor;
    }

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON})

    public OutputNews getNews(){
        return interactor.execute();
    }


}
