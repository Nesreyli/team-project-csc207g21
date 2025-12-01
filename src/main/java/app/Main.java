package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addLoggedInView()
                .addSignupView()
                .addPortfolioView()
                .addWatchlistView()
                .addSearchView()
                .addBuySellView()
                .addLeaderboardView()
                .addWatchlistUseCase()
                .addAddToWatchlistUseCase()
                .addRemoveFromWatchlistUseCase()
                .addPriceView()
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
                .addPriceUseCase()
                .addLeaderboardUseCase()
                .addBuySellUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
