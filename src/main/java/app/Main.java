package app;

import UI.UIFont;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UIFont.setGlobalFont();

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addDashboardView()
                .addLoginUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
