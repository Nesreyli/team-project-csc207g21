package Entity;

import java.util.Map;

public class Leaderboard {
    private User user;
    private Map<String, Object> rankedUsers;
    private Map<String, Object> userValues;

    public Leaderboard(){
    }

    public Map<String, Object> getRankedUsers() {
        return rankedUsers;
    }

    public Map<String, Object> getUserValues() {
        return userValues;
    }

    public User getUser() {
        return user;
    }


    public static class Builder{
        private final Leaderboard leaderboard;
        public Builder(){
            leaderboard = new Leaderboard();
        }
        public Builder user(User user){
            leaderboard.user = user;
            return this;
        }
        public Builder rankedUsers(Map<String, Object> rankedUsers){
            leaderboard.rankedUsers = rankedUsers;
            return this;
        }
        public Builder userValues(Map<String, Object> userValues){
            leaderboard.userValues = userValues;
            return this;
        }
        public Leaderboard build() {
            return leaderboard;
        }
    }

}
