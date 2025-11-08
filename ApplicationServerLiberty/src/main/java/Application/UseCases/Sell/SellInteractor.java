package Application.UseCases.Sell;

import Application.Entities.OrderTicket;
import Application.UseCases.PortfolioDBInterface;
import Application.UseCases.User.UserDatabaseInterface;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SellInteractor {
    @Inject
    UserDatabaseInterface userDB;
    @Inject
    PortfolioDBInterface portfolioDB;
    public OutputDataSell executeMarketSell(MarketSellInput sellInput){
        int userID = -1;
        int amount = 0;
        try{
            userID = userDB.getUserID(sellInput.getUsername(),sellInput.getPassword());
            amount = Integer.parseInt(sellInput.getAmount());
        } catch (RuntimeException e) {
            return new OutputDataSell("400");
        }
        if(amount <= 0){
            return new OutputDataSell("400");
        }
        OrderTicket result;
        try{
            result = portfolioDB.sellStock(sellInput.getSymbol(), amount, userID);
        } catch (RuntimeException e) {
            return new OutputDataSell("400");
        }
        if (result == null) {
            return new OutputDataSell("400");
        }

        return new OutputDataSell("200", result.getOrder(), result.getSymbol(),
                result.getAmount(), result.getPrice(), result.getTotalPrice());

    }
}
