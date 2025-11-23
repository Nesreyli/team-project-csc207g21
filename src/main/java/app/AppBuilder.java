package app;

import DataAccess.UserDataAccessObject;
import DataAccess.PortfolioAccessObject;
import Entity.UserFactory;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.homebutton.HomeController;
import InterfaceAdapter.homebutton.HomePresenter;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.logout.LogoutPresenter;
import InterfaceAdapter.portfolio.PortfolioPresenter;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginPresenter;
import InterfaceAdapter.login.LoginViewModel;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.signup.SignupController;
import InterfaceAdapter.signup.SignupPresenter;
import InterfaceAdapter.signup.SignupViewModel;
import UseCase.Login.LoginInputBoundary;
import UseCase.Login.LogInInteractor;
import UseCase.Login.LoginOutputBoundary;
import UseCase.homebutton.HomeInputBoundary;
import UseCase.homebutton.HomeInteractor;
import UseCase.homebutton.HomeOutputBoundary;
import UseCase.logout.LogoutInputBoundary;
import UseCase.logout.LogoutInteractor;
import UseCase.logout.LogoutOutputBoundary;
import UseCase.portfolio.PortfolioInputBoundary;
import UseCase.portfolio.PortfolioInteractor;
import UseCase.portfolio.PortfolioOutputBoundary;
import UseCase.signup.SignupInputBoundary;
import UseCase.signup.SignupInteractor;
import UseCase.signup.SignupOutputBoundary;
import View.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    // DAO version using a shared external database
    final UserDataAccessObject userDataAccessObject = new UserDataAccessObject(userFactory);
    final PortfolioAccessObject portfolioAccessObject = new PortfolioAccessObject();

    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private PortfolioView portView;
    private PortfolioViewModel portViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }


    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LogInInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addPortfolioView(){
        portViewModel = new PortfolioViewModel();
        portView = new PortfolioView(portViewModel);
        cardPanel.add(portView, portViewModel.getViewName());
        return this;
    }

    public AppBuilder addSignupView(){
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupViewModel.getViewName());
        return this;
    }


    public AppBuilder addPortfolioUseCase(){
        final PortfolioOutputBoundary portfolioOutputBoundary = new PortfolioPresenter(viewManagerModel,
                portViewModel, loggedInViewModel);
        final PortfolioInputBoundary portfolioInputBoundary = new PortfolioInteractor(
                portfolioAccessObject, portfolioOutputBoundary);

        PortfolioController portfolioController = new PortfolioController(portfolioInputBoundary);
        loggedInView.setPortfolioController(portfolioController);
        return this;
    }

    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(portViewModel,
                viewManagerModel, loggedInViewModel);
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);

        HomeController homeController = new HomeController(homeInputBoundary);
        portView.setHomeController(homeController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary= new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LogoutInputBoundary logoutInputBoundary = new LogoutInteractor(logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInputBoundary);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addSignupUseCase(){
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary signupInputBoundary = new SignupInteractor(userDataAccessObject,
                signupOutputBoundary);
        final SignupController signupController = new SignupController(signupInputBoundary);
        signupView.setSignupController(signupController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Panic Trade");
        application.setMinimumSize(new Dimension(540,360));
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
