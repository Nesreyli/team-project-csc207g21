package InterfaceAdapter.leaderboard;

import UseCase.leaderboard.LeaderboardInputBoundary;

public class LeaderboardController {
    private LeaderboardInputBoundary leaderboardInputBoundary;

    public LeaderboardController(LeaderboardInputBoundary leaderboardInputBoundary) {
        this.leaderboardInputBoundary = leaderboardInputBoundary;
    }

    public void execute() {
        leaderboardInputBoundary.execute();
    }
}

