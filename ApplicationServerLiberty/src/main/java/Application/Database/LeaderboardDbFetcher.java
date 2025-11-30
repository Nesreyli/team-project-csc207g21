package Application.Database;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Singleton
public class LeaderboardDbFetcher {
    @Inject
    UserDatabaseAccess userDB;
    @Inject
    PortfolioDatabaseAccess portDB;

    // main thing is when leaderboard is updating previous leaderboard should be sent
    @Asynchronous
    public void fetchLeaderboard(ConcurrentMap<Integer, String> leaderboard, ConcurrentMap<String, Long> valuations){
        while(true){
            // this works but eventually due to race condition its gonna handle request while
            // leaderboard map is updating ..
            ConcurrentSkipListMap<Long, List<String>> portfolioValues = new ConcurrentSkipListMap<>();
            Map<Integer, String> users = userDB.getUserIDs();
            //Can use if null as concurrent map doesn't allow null key or value
            for(Integer id: users.keySet()){
                List<String> usersVal = portfolioValues.get(portDB.getPortfolio(id).getValue());
                if(usersVal == null){
                    usersVal = new ArrayList<String>();
                }
                usersVal.add(users.get(id));
                portfolioValues.put(portDB.getPortfolio(id).getValue(), usersVal);
            }
            // Clear existing leaderboard data
            leaderboard.clear();
            valuations.clear();
            
            Long value;
            int i = 1;
            while(!portfolioValues.isEmpty()){
                var descendVal = portfolioValues.descendingKeySet();
                if(descendVal.isEmpty()){
                    break;
                }
                value = descendVal.getFirst();
                List<String> usersVal = portfolioValues.get(value);
                int j = 0;
                // Process all users with this portfolio value
                while(j < usersVal.size()){
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
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
