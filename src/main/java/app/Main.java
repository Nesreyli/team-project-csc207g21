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
                .addSearchView()
                .addPriceView()
                .addPortfolioUseCase()
                .addSearchUseCase()
                .addLogoutUseCase()
                .addLoginUseCase()
                .addHomeUseCase()
                .addSignupUseCase()
                .addLoggedInUseCase()
                .addPriceUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
