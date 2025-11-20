package InterfaceAdapter;

import Application.UseCases.Leaderboard.OutputLeaderboard;
import java.util.Map;
import java.util.List;

public class LeaderboardPresenter {
    public String retrieveTopUsers (OutputLeaderboard olb, Integer n) {
        Map<Integer, List<Object>> leaderboard = olb.getLeaderboard();
        StringBuilder toReturn = new StringBuilder();

        for (int i = 1; i < n; i++) {
            if (leaderboard.containsKey(i)) {
                toReturn.append(i).append(". ").append(leaderboard.get(i).toString()).append("\n");
            } else {
                break;
            }
        }

        return toReturn.toString();
    }

    public String retrieveNthUser (OutputLeaderboard olb, Integer n) {
        Map<Integer, List<Object>> leaderboard = olb.getLeaderboard();
        String toReturn = "";

        if (leaderboard.containsKey(n)) {
            toReturn = n + ". " + leaderboard.get(n).toString();
        }

        return toReturn;
    }
}