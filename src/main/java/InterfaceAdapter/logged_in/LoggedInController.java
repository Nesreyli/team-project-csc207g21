package InterfaceAdapter.logged_in;

import UseCase.loggedIn.LoggedInInputBoundary;

public class LoggedInController {
    private LoggedInInputBoundary loggedInInputBoundary;

    public LoggedInController(LoggedInInputBoundary loggedInInputBoundary){
        this.loggedInInputBoundary = loggedInInputBoundary;
    }
    public void switchToSearch(){
        loggedInInputBoundary.switchToSearch();
    }
}
