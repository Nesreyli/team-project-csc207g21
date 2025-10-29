package jakarta.UseCases;

public interface UserDatabaseInterface {
    boolean addUser(String username, String password);

    void verifyUser(String username, String password);
    int getUserID(String username);
}
