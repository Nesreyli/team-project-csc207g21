package application.use_case.User;

public interface UserDatabaseInterface {
    boolean addUser(String username, String password);

    Integer getUserID(String username, String password);
}
