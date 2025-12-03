package application.use_case.Leaderboard;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LeaderboardInteractor {
    @Inject
    LeaderboardDBInterface leaderboardDb;

    public OutputLeaderboard executeLeaderboard() {
        try {
            return new OutputLeaderboard("200", leaderboardDb.getLeaderboard().getLeaderboard());
        }
        catch (RuntimeException error) {
            return new OutputLeaderboard("500", null);
        }
    }
}
