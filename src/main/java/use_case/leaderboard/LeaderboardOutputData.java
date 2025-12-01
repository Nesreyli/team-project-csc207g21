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

    /**
     * Returns the leaderboard data.
     *
     * @return a map where the key is the rank and the value is a list of user-related data
     */
    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }

    /**
     * Indicates whether there are enough users to display a meaningful leaderboard.
     *
     * @return {@code true} if there are at least two users, {@code false} otherwise
     */
    public boolean hasEnoughUsers() {
        return hasEnoughUsers;
    }
}

