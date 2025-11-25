package UseCase.leaderboard;

/**
 * The output boundary for the Login Use Case.
 */
public interface LeaderboardOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareLeaderboardSuccessView(LeaderboardOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareLeaderboardFailView(String errorMessage);
}
