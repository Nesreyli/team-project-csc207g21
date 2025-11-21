package app;
import UseCase.news.NewsFeedInteractor;
import UseCase.news.OutputNews;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/portfolio")
public class NewsFeedResource {
    @Inject
    private NewsFeedInteractor interactor;
    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNews() {
        try {
            OutputNews result = interactor.execute();
            if (result == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Error: news API returned null").build();
            }
            return Response.ok(result).build();

        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Server error while retrieving news").build();
        }
    }
}
