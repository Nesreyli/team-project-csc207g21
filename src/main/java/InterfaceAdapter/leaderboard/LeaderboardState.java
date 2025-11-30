package InterfaceAdapter.leaderboard;

import java.util.List;
import java.util.Map;

public class LeaderboardState {
    private Map<Integer, List<Object>> leaderboard;
    private boolean hasEnoughUsers;
    private String error;

    public LeaderboardState() {
    }

    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }

    public LeaderboardState setLeaderboard(Map<Integer, List<Object>> leaderboard) {
        this.leaderboard = leaderboard;
        return this;
    }

    public boolean hasEnoughUsers() {
        return hasEnoughUsers;
    }

    public LeaderboardState setHasEnoughUsers(boolean hasEnoughUsers) {
        this.hasEnoughUsers = hasEnoughUsers;
        return this;
    }

    public String getError() {
        return error;
    }

    public LeaderboardState setError(String error) {
        this.error = error;
        return this;
    }
}

