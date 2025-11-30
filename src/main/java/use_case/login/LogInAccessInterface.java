package use_case.login;

public interface LogInAccessInterface {
    entity.Response logIn(String username, String password);
}
