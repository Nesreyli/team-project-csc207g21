package use_case.login;

public interface LogInAccessInterface {
    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return a Response object containing the status code and,
     *         if successful, the corresponding {@code User} entity
     */
    entity.Response logIn(String username, String password);
}
