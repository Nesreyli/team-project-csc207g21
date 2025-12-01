package use_case.leaderboard;

import java.util.List;
import java.util.Map;

public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private final LeaderboardAccessInterface leaderboardAccessInterface;
    private final LeaderboardOutputBoundary leaderboardOutputBoundary;

    public LeaderboardInteractor(LeaderboardAccessInterface leaderboardAccessInterface,
                                 LeaderboardOutputBoundary leaderboardOutputBoundary) {
        this.leaderboardAccessInterface = leaderboardAccessInterface;
        this.leaderboardOutputBoundary = leaderboardOutputBoundary;
    }

    /**
     * Executes the Leaderboard use case.
     * Fetches the leaderboard from the data access interface and sends the
     * results to the output boundary. Handles both success and failure scenarios.
     */
    public void execute() {
        try {
            final entity.Response response = leaderboardAccessInterface.getLeaderboard();
            switch (response.getStatus_code()) {
                case 200:
                    @SuppressWarnings("unchecked")
                    final Map<Integer, List<Object>> leaderboard = (Map<Integer, List<Object>>) response.getEntity();
                    final LeaderboardOutputData leaderboardOutputData = new LeaderboardOutputData(leaderboard);
                    leaderboardOutputBoundary.prepareSuccessView(leaderboardOutputData);
                    break;
                case 400:
                default:
                    leaderboardOutputBoundary.prepareFailView("Failed to load leaderboard");
                    break;
            }
        }
        catch (RuntimeException error) {
            leaderboardOutputBoundary.prepareFailView("Server Error: " + error.getMessage());
        }
    }
}

