package Application.UseCases.Portfolio;

import Application.Entities.Portfolio;
import Application.UseCases.Buy.OutputDataBuy;
import Application.UseCases.User.UserDatabaseInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioInteractor {

    @Inject
    UserDatabaseInterface userDB;
    @Inject
    PortfolioDBInterface portDB;

    public OutputPortfolio executePortfolio(String username, String password){
        Integer userID;
        try{
            userID = userDB.getUserID(username, password);
            if(userID == null){
                return new OutputPortfolio("400");
            }
            Portfolio p = portDB.getPortfolio(userID);

            return new OutputPortfolio("200", username, p.getCash(), p.getHoldings(),
                    p.getValue(), p.getPerformance());

        } catch (RuntimeException e) {
            return new OutputPortfolio("400");
        }
    }
}
