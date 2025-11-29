package app;

import DataAccess.PriceAccessObject;
import DataAccess.NewsAccessObject;
import DataAccess.UserDataAccessObject;
import DataAccess.PortfolioAccessObject;
import DataAccess.SearchAccessObject;
import DataAccess.UserDataAccessObject;
import Entity.Price;
import Entity.UserFactory;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.homebutton.HomeController;
import InterfaceAdapter.homebutton.HomePresenter;
import InterfaceAdapter.logged_in.LoggedInController;
import InterfaceAdapter.logged_in.LoggedInPresenter;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.logout.LogoutPresenter;
import InterfaceAdapter.news.NewsController;
import InterfaceAdapter.news.NewsPresenter;
import InterfaceAdapter.news.NewsViewModel;
import InterfaceAdapter.portfolio.PortfolioPresenter;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginPresenter;
import InterfaceAdapter.login.LoginViewModel;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.Stock_Search.SearchController;
import InterfaceAdapter.Stock_Search.SearchPresenter;
import InterfaceAdapter.Stock_Search.SearchViewModel;
import InterfaceAdapter.signup.SignupController;
import InterfaceAdapter.signup.SignupPresenter;
import InterfaceAdapter.signup.SignupViewModel;
import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PricePresenter;
import InterfaceAdapter.stock_price.PriceViewModel;
import UseCase.Login.LoginInputBoundary;
import UseCase.Login.LogInInteractor;
import UseCase.Login.LoginOutputBoundary;
import UseCase.homebutton.HomeInputBoundary;
import UseCase.homebutton.HomeInteractor;
import UseCase.homebutton.HomeOutputBoundary;
import UseCase.loggedIn.LoggedInInputBoundary;
import UseCase.loggedIn.LoggedInInteractor;
import UseCase.loggedIn.LoggedInOutputBoundary;
import UseCase.logout.LogoutInputBoundary;
import UseCase.logout.LogoutInteractor;
import UseCase.logout.LogoutOutputBoundary;
import UseCase.news.NewsInputBoundary;
import UseCase.news.NewsInteractor;
import UseCase.news.NewsOutputBoundary;
import UseCase.portfolio.PortfolioInputBoundary;
import UseCase.portfolio.PortfolioInteractor;
import UseCase.portfolio.PortfolioOutputBoundary;
import UseCase.signup.SignupInputBoundary;
import UseCase.signup.SignupInteractor;
import UseCase.signup.SignupOutputBoundary;
import UseCase.stock_price.PriceInputBoundary;
import UseCase.stock_price.PriceOutputBoundary;
import UseCase.stock_price.StockPriceInteractor;
import View.*;
import UseCase.Stock_Search.SearchInputBoundary;
import UseCase.Stock_Search.SearchInteractor;
import UseCase.Stock_Search.SearchOutputBoundary;
import View.LoggedInView;
import View.LoginView;
import View.PortfolioView;
import View.SearchView;
import View.ViewManager;

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
    final NewsAccessObject newsAccessObject = new NewsAccessObject();
    final SearchAccessObject  searchDataAccess = new SearchAccessObject();  // ✅ Add this
    final PriceAccessObject priceAccessObject = new PriceAccessObject();

    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private PortfolioView portView;
    private PortfolioViewModel portViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private NewsViewModel newsViewModel;
    private NewsPanel newsPanel;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private StockPriceView stockPriceView;
    private PriceViewModel priceViewModel;

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

    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addSignupView(){
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupViewModel.getViewName());
        return this;
    }

    public AppBuilder addPriceView(){
        priceViewModel = new PriceViewModel();
        stockPriceView = new StockPriceView(priceViewModel);
        stockPriceView.setLocationRelativeTo(searchView);
        return this;
    }

    public AppBuilder addPriceUseCase(){
        final PriceOutputBoundary priceOutputBoundary = new PricePresenter(viewManagerModel,
                searchViewModel, priceViewModel);
        final PriceInputBoundary priceInputBoundary = new StockPriceInteractor(
                priceAccessObject, priceOutputBoundary);

        PriceController priceController = new PriceController(priceInputBoundary);
        searchView.setPriceController(priceController);
        return this;
    }

    public AppBuilder addNewsView(){
        newsViewModel = new NewsViewModel();
        newsPanel = new NewsPanel(newsViewModel);
        cardPanel.add(newsPanel, newsViewModel.getViewName());
        return this;
    }

    public AppBuilder addNewsUseCase(){
        final NewsOutputBoundary newsOutputBoundary = new NewsPresenter(viewManagerModel,
                newsViewModel);
        final NewsInputBoundary newsInputBoundary = new NewsInteractor(newsAccessObject, newsOutputBoundary);
        final NewsController newsController = new NewsController(newsInputBoundary);

        loggedInView.setNewsController(newsController);
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

    public AppBuilder addSearchUseCase(){
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel,
                viewManagerModel, loggedInViewModel);
        final HomeOutputBoundary homeOutputBoundary = (HomeOutputBoundary) searchOutputBoundary;
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);

        final SearchInputBoundary searchInteractor = new SearchInteractor(searchDataAccess, searchOutputBoundary);
        SearchController searchController = new SearchController(searchInteractor, homeInputBoundary);
        searchView.setSearchController(searchController);
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

    public AppBuilder addLoggedInUseCase(){
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(loggedInViewModel,
                viewManagerModel, searchViewModel);
        final LoggedInInputBoundary loggedInInputBoundary = new LoggedInInteractor(loggedInOutputBoundary);

        final LoggedInController loggedInController = new LoggedInController(loggedInInputBoundary);
        loggedInView.setLoggedInController(loggedInController);
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
