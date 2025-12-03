package application.use_case.Portfolio;

import application.entities.Portfolio;
import application.use_case.User.UserDatabaseInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioInteractor {

    @Inject
    UserDatabaseInterface userDb;
    @Inject
    PortfolioDBInterface portDb;

    /**
     * Retrieves a user's portfolio information based on their credentials.
     * @param username the username of the user requesting portfolio details
     * @param password the password corresponding to the provided username
     * @return an {@link OutputPortfolio} containing
     *          status code and portfolio details if successful
     *          status code if authentication fails or an error occurs
     */
    public OutputPortfolio executePortfolio(String username, String password) {
        final Integer userID;
        try {
            userID = userDb.getUserID(username, password);
            if (userID == null) {
                return new OutputPortfolio("400");
            }
            final Portfolio p = portDb.getPortfolio(userID);

            return new OutputPortfolio("200", username, p.getCash(), p.getHoldings(),
                    p.getValue(), p.getPerformance());

        }
        catch (RuntimeException error) {
            return new OutputPortfolio("400");
        }
    }
}
