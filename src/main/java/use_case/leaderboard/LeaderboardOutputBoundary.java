package use_case.leaderboard;

public interface LeaderboardOutputBoundary {
    void prepareSuccessView(LeaderboardOutputData leaderboardOutputData);
    void prepareFailView(String message);
}

