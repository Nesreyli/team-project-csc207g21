package application.controller;

import application.use_case.Leaderboard.LeaderboardInteractor;
import application.use_case.Leaderboard.OutputLeaderboard;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/leaderboard")
public class LeaderboardResource {

    @Inject
    private LeaderboardInteractor leaderboardUseCase;

    /**
     * Endpoint for leaderboard.
     * @return JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get")
    public OutputLeaderboard getLeaderboard() {
        return leaderboardUseCase.executeLeaderboard();
    }
}
