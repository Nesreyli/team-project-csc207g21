package use_case.homebutton;

public class HomeOutputData {
    private final String username;
    private final String password;

    public HomeOutputData(String user, String pass) {
        username = user;
        password = pass;
    }

    /**
     * Getter.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return password/
     */
    public String getPassword() {
        return password;
    }

}
