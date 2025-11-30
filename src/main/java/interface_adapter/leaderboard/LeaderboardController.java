package interface_adapter.leaderboard;

import use_case.leaderboard.LeaderboardInputBoundary;

public class LeaderboardController {
    private LeaderboardInputBoundary leaderboardInputBoundary;

    public LeaderboardController(LeaderboardInputBoundary leaderboardInputBoundary) {
        this.leaderboardInputBoundary = leaderboardInputBoundary;
    }

    public void execute() {
        leaderboardInputBoundary.execute();
    }
}

