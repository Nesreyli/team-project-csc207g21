package app;

import DataAccess.DBUserDataAccessObject;
import DataAccess.PortfolioAccessObject;
import Entity.UserFactory;
import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginPresenter;
import InterfaceAdapter.login.LoginViewModel;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.portfolio.PortfolioPresenter;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.usersession.UserSessionViewModel;
import UseCase.login.LogInInteractor;
import UseCase.logout.LogoutInputBoundary;
import UseCase.portfolio.PortfolioInteractor;
import UseCase.portfolio.PortfolioOutputBoundary;
import View.DashboardPanel;
import View.LoginView;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final UserFactory userFactory = new UserFactory();
    private final DBUserDataAccessObject userDAO = new DBUserDataAccessObject(userFactory);
    private final PortfolioAccessObject portfolioDAO = new PortfolioAccessObject();

    private final UserSessionViewModel userSessionViewModel = new UserSessionViewModel();
    private final PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private LogoutInputBoundary logoutUseCaseInteractor;

    private PortfolioController portfolioController;
    private final LogoutController logoutController = new LogoutController(logoutUseCaseInteractor);

    private LoginView loginView;
    private DashboardPanel dashboardPanel;

    public AppBuilder addLoginView() {
        loginView = new LoginView(loginViewModel, userSessionViewModel);
        cardPanel.add(loginView, "Login");
        return this;
    }

    public AppBuilder addDashboardView() {

        PortfolioOutputBoundary outputBoundary = new PortfolioPresenter(portfolioViewModel, userSessionViewModel);
        PortfolioInteractor portfolioInteractor = new PortfolioInteractor(portfolioDAO, outputBoundary);
        portfolioController = new PortfolioController(portfolioInteractor);

        dashboardPanel = new DashboardPanel(
                portfolioController,
                logoutController,
                userSessionViewModel,
                portfolioViewModel
        );

        cardPanel.add(dashboardPanel, "Dashboard");
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginPresenter loginPresenter = new LoginPresenter(userSessionViewModel, loginViewModel);
        LogInInteractor loginInteractor = new LogInInteractor(userDAO, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);

        userSessionViewModel.addPropertyChangeListener(evt -> {
            UserSessionViewModel.UserSessionState state =
                    (UserSessionViewModel.UserSessionState) evt.getNewValue();

            if (state.loggedIn()) {
                SwingUtilities.invokeLater(() -> {

                    CardLayout layout = (CardLayout) cardPanel.getLayout();
                    layout.show(cardPanel, "Dashboard");

                    String username = state.username();
                    String password = state.password();
                    portfolioController.execute(username, password);
                    });
            }
        });

        return this;
    }

    public JFrame build() {
        JFrame frame = new JFrame("G21 Traders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1200, 800));
        frame.add(cardPanel);
        frame.setVisible(true);

        return frame;
    }
}
