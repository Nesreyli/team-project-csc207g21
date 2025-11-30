package application.use_case.Sell;

import application.entities.OrderTicket;
import application.use_case.Portfolio.PortfolioDBInterface;
import application.use_case.User.UserDatabaseInterface;
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
            return new OutputDataSell("401");
        }
        if(amount <= 0){
            return new OutputDataSell("401");
        }
        OrderTicket result;
        try{
            result = portfolioDB.sellStock(sellInput.getSymbol(), amount, userID);
        } catch (RuntimeException e) {
            return new OutputDataSell("500");
        }
        if (result == null) {
            return new OutputDataSell("400");
        }

        return new OutputDataSell("200", result.getOrder(), result.getSymbol(),
                result.getAmount(), result.getPrice(), result.getTotalPrice());

    }
}
