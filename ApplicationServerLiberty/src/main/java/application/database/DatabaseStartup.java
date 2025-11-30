package application.database;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class DatabaseStartup {
    @Inject
    private PriceDatabaseAccess priceDb;
    @Inject
    private PortfolioDatabaseAccess portDb;
    @Inject
    private LeaderboardDatabaseAccess lbDb;
    @Inject
    private WatchlistDatabaseAccess watchlistDatabaseAccess;

    /**
     * Singleton bean that eagerly starts price DB.
     * @throws InterruptedException exception
     */
    @PostConstruct
    public void init() throws InterruptedException {
        priceDb.init();
    }
}
