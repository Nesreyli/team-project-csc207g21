package Entity;

/**
 * Factory for creating CommonUser objects.
 */
public class UserFactory {

    public static User create(String name, String password) {
        return new User(name, password);
    }
}