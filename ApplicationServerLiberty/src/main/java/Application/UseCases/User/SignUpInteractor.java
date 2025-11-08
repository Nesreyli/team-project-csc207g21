package Application.UseCases.User;

import Application.UseCases.Buy.OutputDataBuy;
import Application.UseCases.PortfolioDBInterface;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class SignUpInteractor {
    @Inject
    UserDatabaseInterface usersDB;
    @Inject
    PortfolioDBInterface portDB;

    // if password not match handle here if username exists DB access returns false
    public OutputDataSignup executeSignup(String username, String password, String password2) {
        if (password.equals(password2) && password.length() >= 5) {
            if (usersDB.addUser(username, password)) {
                try{
                    portDB.newUser(usersDB.getUserID(username, password));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return new OutputDataSignup("500", null);
                }
                return new OutputDataSignup("201", username);
            }
        }
        return new OutputDataSignup("400", null);
    }

    public OutputDataSignup executeLogin(String username, String password) {
        try{
            if(usersDB.getUserID(username, password) == null){
                return new OutputDataSignup("400", null);
            }
        } catch (RuntimeException e) {
            return new OutputDataSignup("400", null);
        }
        return new OutputDataSignup("200", username);
    }
}
