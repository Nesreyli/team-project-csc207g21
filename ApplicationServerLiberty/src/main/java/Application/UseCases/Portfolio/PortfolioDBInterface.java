package Application.UseCases.Portfolio;

import Application.Entities.OrderTicket;
import Application.Entities.Portfolio;

public interface PortfolioDBInterface {
    // return void throw exception or boolean
    void newUser(int id);

    OrderTicket buyStock(String symbol, int amount, int id);
    OrderTicket sellStock(String symbol, int amount, int id);

    public Portfolio getPortfolio(int user_id);
}
