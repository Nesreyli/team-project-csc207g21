package UseCase.leaderboard;

/**
 * The Input Data for the Login Use Case.
 */
public class LeaderboardInputData {

    private final String username;
    private final String password;

    public LeaderboardInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
