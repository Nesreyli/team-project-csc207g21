package Application.Database;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

@ApplicationScoped
public class LeaderboardDatabaseAccess {
    @Inject
    LeaderboardDbFetcher leaderboardFetch;

    ConcurrentMap<Integer, String> leaderboard = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        leaderboardFetch.fetchLeaderboard(leaderboard);
    }

    public Map<Integer, String> getLeaderboard(){
        return leaderboard;
    }
}
