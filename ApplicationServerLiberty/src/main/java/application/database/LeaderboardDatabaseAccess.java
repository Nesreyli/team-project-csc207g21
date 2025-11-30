package application.database;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import application.entities.Leaderboard;
import application.use_case.Leaderboard.LeaderboardDBInterface;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LeaderboardDatabaseAccess implements LeaderboardDBInterface {

    private static final int DELAY = 500;

    @Inject
    private LeaderboardDbFetcher leaderboardFetch;

    private ConcurrentMap<Integer, String> leaderboard = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Long> valuations = new ConcurrentHashMap<>();

    /**
     * Calls asynchronous leaderboard backend after construction.
     * @throws InterruptedException exception
     */
    @PostConstruct
    public void init() throws InterruptedException {
        leaderboardFetch.fetchLeaderboard(leaderboard, valuations);
        // have to do this until i find a way to eagerly run fetchleaderboard after price is fetched and valuation
        // calculated in portfolio
        Thread.sleep(DELAY);
    }

    /**
     * Gets leaderboard top 10.
     * @return Leaderboard
     */
    public Leaderboard getLeaderboard() {
        return new Leaderboard(leaderboard, valuations);
    }
}
