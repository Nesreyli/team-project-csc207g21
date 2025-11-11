package Application.Database;

import Application.Entities.Leaderboard;
import Application.UseCases.Leaderboard.LeaderboardDBInterface;
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
public class LeaderboardDatabaseAccess implements LeaderboardDBInterface {
    @Inject
    LeaderboardDbFetcher leaderboardFetch;

    ConcurrentMap<Integer, String> leaderboard = new ConcurrentHashMap<>();
    ConcurrentMap<String, Long> valuations = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws InterruptedException {
        leaderboardFetch.fetchLeaderboard(leaderboard, valuations);
        // have to do this until i find a way to eagerly run fetchleaderboard after price is fetched and valuation
        // calculated in portfolio
        Thread.sleep(500);
    }

    public Leaderboard getLeaderboard(){
        return new Leaderboard(leaderboard, valuations);
    }
}
