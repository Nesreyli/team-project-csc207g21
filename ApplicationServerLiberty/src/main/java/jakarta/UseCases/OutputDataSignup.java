package jakarta.UseCases;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class OutputDataSignup {
    String message;
    String username;

    public OutputDataSignup(String m, String name){
        message = m;
        username = name;
    }

    public String getMessage() {
        return message;
    }
    public String getUsername() {
        return username;
    }
}
