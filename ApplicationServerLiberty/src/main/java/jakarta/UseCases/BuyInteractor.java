package jakarta.UseCases;

import jakarta.Database.UserDatabaseAccess;
import jakarta.Entities.OrderTicket;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class BuyInteractor {
    @Inject
    UserDatabaseInterface userDB;
    @Inject
    PortfolioDBInterface portfolioDB;
    public OutputDataBuy executeMarketBuy(MarketBuyInput buyInput){
        int userID = -1;
        int amount = 0;
        try{
            userID = userDB.getUserID(buyInput.getUsername(),buyInput.getPassword());
            amount = Integer.parseInt(buyInput.getAmount());
        } catch (RuntimeException e) {
            return new OutputDataBuy("400");
        }
        if(amount <= 0){
            return new OutputDataBuy("400");
        }
        OrderTicket result;
        try{
            result = portfolioDB.buyStock(buyInput.getSymbol(), amount, userID);
        } catch (RuntimeException e) {
            return new OutputDataBuy("500");
        }
        if (result == null) {
            return new OutputDataBuy("400");
        }

        return new OutputDataBuy("200", result.getOrder(), result.getSymbol(),
                result.getAmount(), result.getPrice(), result.getTotalPrice());

    }
}
