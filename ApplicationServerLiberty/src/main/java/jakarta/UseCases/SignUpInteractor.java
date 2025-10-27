package jakarta.UseCases;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class SignUpInteractor {
    @Inject
    UserDatabaseInterface usersDB;

    // if password not match handle here if username exists DB access returns false
    public OutputDataSignup executeSignup(String username, String password, String password2) {
        if (password.equals(password2) && password.length() >= 5) {
            if (usersDB.addUser(username, password)) {
                return new OutputDataSignup("201", username);
            }
            return new OutputDataSignup("400", null);
        }
        return new OutputDataSignup("400", null);
    }
}
