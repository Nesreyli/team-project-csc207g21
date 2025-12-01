package use_case.leaderboard;

public interface LeaderboardAccessInterface {
    /**
     * Retrieves the leaderboard data.
     * @return a Response object containing the leaderboard data or
     *         information about why the retrieval failed
     */
    entity.Response getLeaderboard();
}

