package app;

import javax.swing.*;

/**
 * Initializes the application by using AppBuilder to add all required views
 * and use cases, then builds the main JFrame representing the app window.
 * The window is packed, centered on the screen, and made visible.
 */

public class Main {
    /**
     * Starts the application.
     * Creates an AppBuilder, adds views and use cases, then builds the main
     * application window. The window is packed, positioned at the screen center,
     * and displayed.
     *
     * @param args command-line arguments (not used)
     */
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
                .addWatchlistUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
