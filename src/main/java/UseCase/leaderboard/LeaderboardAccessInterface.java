package UseCase.leaderboard;

public interface LeaderboardAccessInterface {
    public Entity.Response getLeaderboard(String username, String password);
}
