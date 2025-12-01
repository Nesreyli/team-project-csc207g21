package use_case.leaderboard;

import java.util.List;
import java.util.Map;

public class LeaderboardOutputData {
    private Map<Integer, List<Object>> leaderboard;
    private boolean hasEnoughUsers;

    public LeaderboardOutputData(Map<Integer, List<Object>> leaderboard) {
        this.leaderboard = leaderboard;
        this.hasEnoughUsers = leaderboard != null && leaderboard.size() >= 2;
    }

    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }

    public boolean hasEnoughUsers() {
        return hasEnoughUsers;
    }
}

