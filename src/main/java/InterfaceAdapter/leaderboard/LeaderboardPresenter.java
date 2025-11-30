package InterfaceAdapter.leaderboard;

import InterfaceAdapter.ViewManagerModel;
import UseCase.leaderboard.LeaderboardOutputBoundary;
import UseCase.leaderboard.LeaderboardOutputData;

public class LeaderboardPresenter implements LeaderboardOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private LeaderboardViewModel leaderboardViewModel;

    public LeaderboardPresenter(ViewManagerModel viewManagerModel,
                                LeaderboardViewModel leaderboardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.leaderboardViewModel = leaderboardViewModel;
    }

    @Override
    public void prepareSuccessView(LeaderboardOutputData leaderboardOutputData) {
        LeaderboardState leaderboardState = leaderboardViewModel.getState();
        leaderboardState.setLeaderboard(leaderboardOutputData.getLeaderboard());
        leaderboardState.setHasEnoughUsers(leaderboardOutputData.hasEnoughUsers());
        leaderboardViewModel.firePropertyChange();

        viewManagerModel.setState("leaderboard");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        LeaderboardState leaderboardState = leaderboardViewModel.getState();
        leaderboardState.setError(message);
        leaderboardViewModel.firePropertyChange();

        viewManagerModel.setState("leaderboard");
        viewManagerModel.firePropertyChange();
    }
}

