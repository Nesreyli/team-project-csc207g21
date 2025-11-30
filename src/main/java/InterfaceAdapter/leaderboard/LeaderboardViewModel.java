package InterfaceAdapter.leaderboard;

import InterfaceAdapter.ViewModel;

public class LeaderboardViewModel extends ViewModel<LeaderboardState> {
    public LeaderboardViewModel() {
        super("leaderboard");
        setState(new LeaderboardState());
    }
}

