package Application.Database;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class DatabaseStartup {
    @Inject
    PriceDatabaseAccess priceDB;
    @Inject
    PortfolioDatabaseAccess portDB;
    @Inject
    LeaderboardDatabaseAccess lbDB;
    @Inject
    WatchlistDatabaseAccess watchlistDB;

    public DatabaseStartup(){}

    @PostConstruct
    public void init() throws InterruptedException {
        priceDB.init();
    }
}
