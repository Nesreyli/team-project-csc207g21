package InterfaceAdapter.leaderboard;

import UseCase.leaderboard.LeaderboardOutputData;

public class LeaderboardPresenter {

    public static LeaderboardViewModel.LeaderboardUIState convertToViewModel(LeaderboardOutputData response) {
        return new LeaderboardViewModel.LeaderboardUIState(
                response.getRankedUsers(),
                response.getUserValues()
        );
    }
}
