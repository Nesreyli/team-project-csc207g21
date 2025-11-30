package application.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class LeaderboardDbFetcher {
    private static final int TOP10 = 10;
    private static final int REST = 60000;
    @Inject
    private UserDatabaseAccess userDb;
    @Inject
    private PortfolioDatabaseAccess portDb;

    /**
     * Asynchronous leaderboard calc.
     * @param leaderboard leaderboard in map
     * @param valuations valuations of map.
     * @throws  RuntimeException when thread sleep error
     */
    // main thing is when leaderboard is updating previous leaderboard should be sent
    @Asynchronous
    public void fetchLeaderboard(ConcurrentMap<Integer, String> leaderboard, ConcurrentMap<String, Long> valuations) {
        while (true) {
            // this works but eventually due to race condition its gonna handle request while
            // leaderboard map is updating ..
            final ConcurrentSkipListMap<Long, List<String>> portfolioValues = new ConcurrentSkipListMap<>();
            final Map<Integer, String> users = userDb.getUserIDs();
            // Can use if null as concurrent map doesn't allow null key or value
            for (Integer id: users.keySet()) {
                List<String> usersVal = portfolioValues.get(portDb.getPortfolio(id).getValue());
                if (usersVal == null) {
                    usersVal = new ArrayList<String>();
                }
                usersVal.add(users.get(id));
                portfolioValues.put(portDb.getPortfolio(id).getValue(), usersVal);
            }
            Long value;
            int i = 1;
            while (i <= TOP10) {
                final var descendVal = portfolioValues.descendingKeySet();
                if (descendVal.isEmpty()) {
                    break;
                }
                value = descendVal.getFirst();
                final List<String> usersVal = portfolioValues.get(value);
                int j = 0;
                while (j < usersVal.size() && i <= TOP10) {
                    leaderboard.put(i, usersVal.get(j));
                    valuations.put(usersVal.get(j), value);
                    j++;
                    i++;
                }
                portfolioValues.pollLastEntry();
            }
            System.out.println(Thread.currentThread());
            System.out.println("Async Method Leaderboard fetch working");
            try {
                Thread.sleep(REST);
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
