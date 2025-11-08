package Application.UseCases;

import Application.Entities.OrderTicket;

public interface PortfolioDBInterface {
    // return void throw exception or boolean
    void newUser(int id);

    OrderTicket buyStock(String symbol, int amount, int id);
    OrderTicket sellStock(String symbol, int amount, int id);
}
