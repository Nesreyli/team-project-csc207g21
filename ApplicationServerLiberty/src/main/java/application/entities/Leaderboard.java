package application.entities;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RequestScoped
public class Leaderboard {
    private Map<Integer, List<Object>> leaderboard = new HashMap<>();

    public Leaderboard(){};

    public Leaderboard(Map<Integer, String> users, Map<String, Long> value){
        for(Integer num: users.keySet()){
            String username = users.get(num);
            if (username == null || username.isEmpty()) {
                continue; // Skip entries with null or empty usernames
            }
            List<Object> ranking = new ArrayList<>();
            ranking.add(username);
            Long portfolioValue = value.get(username);
            if (portfolioValue != null) {
                ranking.add(new BigDecimal(portfolioValue).divide(new BigDecimal(100000), 5,
                        RoundingMode.UNNECESSARY));
            } else {
                ranking.add(BigDecimal.ZERO); // Default to 0 if valuation is missing
            }
            leaderboard.put(num, ranking);
        }
    }

    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }
}
