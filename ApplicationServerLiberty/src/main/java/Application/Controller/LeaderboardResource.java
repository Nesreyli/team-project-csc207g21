package Application.Controller;

import Application.Database.LeaderboardDatabaseAccess;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/leaderboard")
public class LeaderboardResource {
    @Inject
    LeaderboardDatabaseAccess leaderboardUseCase;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/get")
    public Map<Integer, String> getLeaderboard(){
        return leaderboardUseCase.getLeaderboard();
    }
}
