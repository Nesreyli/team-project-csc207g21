package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addLoggedInView()
                .addSignupView()
                .addPortfolioView()
                .addNewsView()
                .addSearchView()
                .addNewsUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addPortfolioUseCase()
                .addSearchUseCase()
                .addLogoutUseCase()
                .addLoginUseCase()
                .addHomeUseCase()
                .addSignupUseCase()
                .addLoggedInUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
