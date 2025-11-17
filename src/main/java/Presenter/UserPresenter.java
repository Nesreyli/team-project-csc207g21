package Presenter;

import Application.UseCases.User.OutputDataSignup;

public class UserPresenter {
    public String retrieveUsername (OutputDataSignup odsu) {
        return odsu.getUsername();
    }
}