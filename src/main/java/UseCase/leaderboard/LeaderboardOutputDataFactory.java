package UseCase.leaderboard;

import Entity.Leaderboard;

public class LeaderboardOutputDataFactory {
    public static LeaderboardOutputData create(Leaderboard l){
        return new LeaderboardOutputData(l.getUser().getName(),
                l.getUser().getPassword(), l.getRankedUsers(), l.getUserValues());
    }
}
