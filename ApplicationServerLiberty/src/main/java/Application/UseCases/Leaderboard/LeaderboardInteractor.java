package Application.UseCases.Leaderboard;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LeaderboardInteractor {
    @Inject
    LeaderboardDBInterface leaderboardDB;

    public OutputLeaderboard executeLeaderboard(){
        try{
            return new OutputLeaderboard("200", leaderboardDB.getLeaderboard().getLeaderboard());
        } catch (RuntimeException e) {
            return new OutputLeaderboard("500", null);
        }
    }
}
