package UseCase.leaderboard;

import Entity.Leaderboard;
import Entity.Portfolio;
import UseCase.portfolio.PortfolioOutputData;

public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private LeaderboardAccessInterface leaderboardAccessInterface;
    private LeaderboardOutputBoundary leaderboardOutputBoundary;

    public LeaderboardInteractor(LeaderboardAccessInterface leaderboardAccessInterface, LeaderboardOutputBoundary leaderboardOutputBoundary) {
        this.leaderboardAccessInterface = leaderboardAccessInterface;
        this.leaderboardOutputBoundary = leaderboardOutputBoundary;
    }

    public void execute(LeaderboardInputData input) {
        Entity.Response response = leaderboardAccessInterface.getLeaderboard(input.getUsername(), input.getPassword());
        switch (response.getStatus_code()) {
            case 200:
                LeaderboardOutputData leaderboardOutputData =
                        new LeaderboardOutputData(((Leaderboard) response.getEntity()).getUser().getName(),
                                ((Leaderboard) response.getEntity()).getUser().getPassword(), ((Leaderboard) response.getEntity()).getRankedUsers(),
                                ((Leaderboard) response.getEntity()).getUserValues());
                leaderboardOutputBoundary.prepareLeaderboardSuccessView(leaderboardOutputData);
                break;
            case 400:
                leaderboardOutputBoundary.prepareLeaderboardFailView("Incorrect username or password.");
                break;
            case 500:
                leaderboardOutputBoundary.prepareLeaderboardFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }

    }
}
