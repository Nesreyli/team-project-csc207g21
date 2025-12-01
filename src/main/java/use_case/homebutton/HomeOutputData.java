package use_case.homebutton;

public class HomeOutputData {
    private final String username;
    private final String password;

    public HomeOutputData(String user, String pass) {
        username = user;
        password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
