package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addLoggedInView()
                .addLogoutUseCase()
                .addPortfolioView()
                .addSearchView()
                .addPortfolioUseCase()
                .addSearchUseCase()
                .addLoginUseCase()
                .addHomeUseCase()
                .addLoggedInUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
