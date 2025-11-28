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
                .addNewsUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addPortfolioUseCase()
                .addHomeUseCase()
                .addSignupUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
