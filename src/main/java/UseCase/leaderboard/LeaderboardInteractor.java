package UseCase.leaderboard;

import java.util.List;
import java.util.Map;

public class LeaderboardInteractor implements LeaderboardInputBoundary {
    LeaderboardAccessInterface leaderboardAccessInterface;
    LeaderboardOutputBoundary leaderboardOutputBoundary;

    public LeaderboardInteractor(LeaderboardAccessInterface leaderboardAccessInterface,
                                 LeaderboardOutputBoundary leaderboardOutputBoundary) {
        this.leaderboardAccessInterface = leaderboardAccessInterface;
        this.leaderboardOutputBoundary = leaderboardOutputBoundary;
    }

    public void execute() {
        try {
            entity.Response response = leaderboardAccessInterface.getLeaderboard();
            switch (response.getStatus_code()) {
                case 200:
                    @SuppressWarnings("unchecked")
                    Map<Integer, List<Object>> leaderboard = (Map<Integer, List<Object>>) response.getEntity();
                    LeaderboardOutputData leaderboardOutputData = new LeaderboardOutputData(leaderboard);
                    leaderboardOutputBoundary.prepareSuccessView(leaderboardOutputData);
                    break;
                case 400:
                case 500:
                    leaderboardOutputBoundary.prepareFailView("Failed to load leaderboard");
                    break;
            }
        } catch (RuntimeException e) {
            leaderboardOutputBoundary.prepareFailView("Server Error: " + e.getMessage());
        }
    }
}

