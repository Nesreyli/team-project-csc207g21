package Application.Entities;

public class User {
    private String username;
    private String password;

    public User(){
    }

    public User(String u, String p, String p2){
        if(p.equals(p2)){
            username = u;
            password = p;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
