package application.use_case.Portfolio;

import application.entities.Portfolio;
import application.use_case.User.UserDatabaseInterface;
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
