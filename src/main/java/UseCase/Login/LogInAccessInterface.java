package UseCase.Login;

public interface LogInAccessInterface {
    Entity.Response logIn(String username, String password);
}
