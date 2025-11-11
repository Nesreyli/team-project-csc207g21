package Application.Controller;

import Application.Database.LeaderboardDatabaseAccess;
import Application.UseCases.Leaderboard.LeaderboardInteractor;
import Application.UseCases.Leaderboard.OutputLeaderboard;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/leaderboard")
public class LeaderboardResource {
    @Inject
    LeaderboardInteractor leaderboardUseCase;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/get")
    public OutputLeaderboard getLeaderboard(){
        return leaderboardUseCase.executeLeaderboard();
    }
}
