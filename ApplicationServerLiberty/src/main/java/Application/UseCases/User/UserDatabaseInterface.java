package Application.UseCases.User;

public interface UserDatabaseInterface {
    boolean addUser(String username, String password);

    Integer getUserID(String username, String password);
}
