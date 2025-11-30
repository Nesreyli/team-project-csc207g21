package interface_adapter.logged_in;

import use_case.loggedIn.LoggedInInputBoundary;

public class LoggedInController {
    private LoggedInInputBoundary loggedInInputBoundary;

    public LoggedInController(LoggedInInputBoundary loggedInInputBoundary){
        this.loggedInInputBoundary = loggedInInputBoundary;
    }
    public void switchToSearch(){
        loggedInInputBoundary.switchToSearch();
    }
}
