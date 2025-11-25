package InterfaceAdapter.usersession;

import InterfaceAdapter.ViewModel;

public class UserSessionViewModel extends ViewModel<UserSessionViewModel.UserSessionState> {

    public UserSessionViewModel() {
        super("userSession");
        setState(new UserSessionState("", "", false));
    }

    public record UserSessionState(
            String username,
            String password,
            boolean loggedIn
    ) {}

    public void login(String username, String password) {
        setState(new UserSessionState(username, password, true));
        firePropertyChange();
    }

    public void logout() {
        setState(new UserSessionState("", "", false));
        firePropertyChange();
    }
}
