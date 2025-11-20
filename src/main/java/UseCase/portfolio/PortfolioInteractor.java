package UseCase.portfolio;

import Entity.Portfolio;
import Entity.User;
import UseCase.Login.LoginOutputData;

public class PortfolioInteractor implements PortfolioInputBoundary{
    private PortfolioAccessInterface portfolioDB;
    private PortfolioOutputBoundary portfolioOutput;

    public PortfolioInteractor(PortfolioAccessInterface portfolio, PortfolioOutputBoundary port){
        portfolioDB = portfolio;
        portfolioOutput = port;
    }

    public void execute(PortfolioInputData input){
        Entity.Response response = portfolioDB.getPort(input.getUsername(), input.getPassword());
        switch(response.getStatus_code()){
            case 200:
                PortfolioOutputData portOutputData =
                        PortfolioOutputDataFactory.create((Portfolio) response.getEntity());
                portfolioOutput.preparePortSuccessView(portOutputData);
                break;
            case 400:
                portfolioOutput.preparePortFailView("Incorrect username or password.");
                break;
            case 500:
                portfolioOutput.preparePortFailView("Server Error");
                break;
            default:
                throw new RuntimeException();
        }
    }
}
