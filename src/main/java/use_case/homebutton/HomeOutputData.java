package use_case.homebutton;

public class HomeOutputData {
    private String username;
    private String password;

    public HomeOutputData(String u, String p){
        username = u;
        password = p;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
