package use_case.leaderboard;

public interface LeaderboardInputBoundary {

    /**
     * Executes the Leaderboard use case.
     * Implementations should retrieve the leaderboard data from the
     * data access interface and pass the results to the output boundary.
     */
    void execute();
}

