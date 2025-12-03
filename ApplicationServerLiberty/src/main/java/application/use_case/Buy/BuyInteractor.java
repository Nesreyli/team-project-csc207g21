package application.use_case.Buy;

import application.entities.OrderTicket;
import application.use_case.Portfolio.PortfolioDBInterface;
import application.use_case.User.UserDatabaseInterface;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class BuyInteractor {
    @Inject
    UserDatabaseInterface userDB;
    @Inject
    PortfolioDBInterface portfolioDB;

<<<<<<< HEAD
    /**
     * execute market buy
     * @param buyInput buy input
     * @return error codes
     */
    public OutputDataBuy executeMarketBuy(MarketBuyInput buyInput){
=======
    public OutputDataBuy executeMarketBuy(MarketBuyInput buyInput) {
>>>>>>> origin/main
        int userID = -1;
        int amount = 0;
        try {
            userID = userDB.getUserID(buyInput.getUsername(), buyInput.getPassword());
            amount = Integer.parseInt(buyInput.getAmount());
        }
        catch (RuntimeException error) {
            return new OutputDataBuy("401");
        }
        if (amount <= 0) {
            return new OutputDataBuy("401");
        }
        final OrderTicket result;
        try {
            result = portfolioDB.buyStock(buyInput.getSymbol(), amount, userID);
        }
        catch (RuntimeException error) {
            return new OutputDataBuy("500");
        }
        if (result == null) {
            return new OutputDataBuy("400");
        }
        return new OutputDataBuy("200", result.getOrder(), result.getSymbol(),
                result.getAmount(), result.getPrice(), result.getTotalPrice());

    }
}
