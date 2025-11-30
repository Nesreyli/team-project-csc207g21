package use_case.loggedIn;

public class LoggedInInteractor implements LoggedInInputBoundary{
    private LoggedInOutputBoundary loggedInOutputBoundary;

    public LoggedInInteractor(LoggedInOutputBoundary loggedInOutputBoundary){
        this.loggedInOutputBoundary = loggedInOutputBoundary;
    }

    public void switchToSearch(){
        loggedInOutputBoundary.switchToSearch();
    }
}
