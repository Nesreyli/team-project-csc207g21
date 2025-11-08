package Application.UseCases.User;

public interface UserDatabaseInterface {
    boolean addUser(String username, String password);

    int getUserID(String username, String password);
}
