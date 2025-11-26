package UseCase.signup;

import Entity.Response;
import Entity.User;

/**
 * DAO interface for the Signup Use Case.
 */
public interface SignupDataAccessInterface {
    /**
     * Saves the user.
     *   the user to save
     */
    Response signUp(String username, String password);
}
