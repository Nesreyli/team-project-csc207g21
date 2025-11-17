package Presenter;

import Application.UseCases.User.OutputDataSignup;

@Singleton
public class UserPresenter {
    public String retrieveUsername (OutputDataSignup odsu) {
        return odsu.getUsername();
    }
}