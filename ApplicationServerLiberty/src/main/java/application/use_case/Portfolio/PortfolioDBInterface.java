package application.use_case.Portfolio;

import application.entities.OrderTicket;
import application.entities.Portfolio;

public interface PortfolioDBInterface {
    // return void throw exception or boolean
    void newUser(int id);

    OrderTicket buyStock(String symbol, int amount, int id);
    OrderTicket sellStock(String symbol, int amount, int id);

    public Portfolio getPortfolio(int user_id);
}
