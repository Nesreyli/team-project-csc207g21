package use_case.leaderboard;

public interface LeaderboardOutputBoundary {
    /**
     * Prepares the view for a successful leaderboard retrieval.
     *
     * @param leaderboardOutputData the data containing the leaderboard information
     */
    void prepareSuccessView(LeaderboardOutputData leaderboardOutputData);

    /**
     * Prepares the view when the leaderboard retrieval fails.
     *
     * @param message an explanation of why the operation failed
     */
    void prepareFailView(String message);
}

