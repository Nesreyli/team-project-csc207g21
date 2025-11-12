package Application.Entities;

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
            List<Object> ranking = new ArrayList<>();
            ranking.add(username);
            ranking.add(new BigDecimal(value.get(username)).divide(new BigDecimal(100000), 5,
                    RoundingMode.UNNECESSARY));
            leaderboard.put(num, ranking);
        }
    }

    public Map<Integer, List<Object>> getLeaderboard() {
        return leaderboard;
    }
}
