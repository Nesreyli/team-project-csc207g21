package application.use_case.Leaderboard;

import jakarta.enterprise.context.RequestScoped;

import java.util.List;
import java.util.Map;

@RequestScoped
public class OutputLeaderboard {
    private String message;
    private Map<Integer, List<Object>> leaderboard;

    public OutputLeaderboard(){}

    public OutputLeaderboard(String m, Map<Integer, List<Object>> l){
        message = m;
        leaderboard = l;
    }

    public String getMessage() {
        return message;
    }

    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }
}

