package InterfaceAdapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String error;
    private String password = "";
    private String passwordError;
    private String repeatPassword = "";

    public String getUsername() {
        return username;
    }

    public String error() {
        return error;
    }

    public String getPassword() {
        return password;
    }


    public String getRepeatPassword() {
        return repeatPassword;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void error(String error) {
        this.error = error;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + '}';
    }
}
