package UseCase.login;

import Entity.User;
import UseCase.UserAccessInterface;

public class LogInInteractor implements LoginInputBoundary{
    private final UserAccessInterface userAccessObject;
    private final LoginOutputBoundary loginOutputBoundary;

    public LogInInteractor(UserAccessInterface userAccessObject,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userAccessObject = userAccessObject;
        this.loginOutputBoundary = loginOutputBoundary;
    }

    public void execute(LoginInputData input){
        Entity.Response response = userAccessObject.logIn(input.getUsername(), input.getPassword());
        switch(response.getStatus_code()){
            case 200:
                LoginOutputData loginOutputData = new LoginOutputData(((User)response.getEntity()).getName(),
                        ((User)response.getEntity()).getPassword());
                loginOutputBoundary.prepareSuccessView(loginOutputData);
                break;
            case 400:
                loginOutputBoundary.prepareFailView("Incorrect username or password.");
                break;
            default:
                loginOutputBoundary.prepareFailView("Error");
                break;
        }
    }
}
