package UseCase.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutOutputBoundary logoutOutputBoundary) {
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        logoutPresenter.prepareSuccessView();
    }
}

