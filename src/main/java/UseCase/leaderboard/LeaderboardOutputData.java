package UseCase.leaderboard;

import Entity.User;

import java.util.Map;

public class LeaderboardOutputData {
    private String username;
    private String password;
    private final Map<String, Object> rankedUsers;
    private final Map<String, Object> userValues;

    public LeaderboardOutputData(String username, String password, Map<String, Object> rankedUsers, Map<String, Object> userValues) {
        this.username = username;
        this.password = password;
        this.rankedUsers = rankedUsers;
        this.userValues = userValues;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public Map<String, Object> getRankedUsers() {
        return rankedUsers;
    }

    public Map<String, Object> getUserValues() {
        return userValues;
    }
}
