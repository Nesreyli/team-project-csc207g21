package jakarta.UseCases;

import jakarta.Entities.OrderTicket;

public interface PortfolioDBInterface {
    // return void throw exception or boolean
    void newUser(int id);

    OrderTicket buyStock(String symbol, int amount, int id);
}
