package UseCase;

public interface UserAccessInterface {
    Entity.Response logIn(String username, String password);
    Entity.Response signUp(String username, String password);
}
