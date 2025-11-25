package InterfaceAdapter.leaderboard;

import UseCase.leaderboard.LeaderboardInputBoundary;
import UseCase.leaderboard.LeaderboardInputData;
import UseCase.leaderboard.LeaderboardOutputData;
import UseCase.portfolio.PortfolioInputData;

import java.util.function.Consumer;

public class LeaderboardController {

    private final LeaderboardInputBoundary leaderboardInteractor;

    public LeaderboardController(LeaderboardInputBoundary leaderboardInteractor){
        this.leaderboardInteractor = leaderboardInteractor;
    }

    public void execute(String username, String password) {
        final LeaderboardInputData leaderboardInputData = new LeaderboardInputData(
                username, password);

       leaderboardInteractor.execute(leaderboardInputData);
    }
}
