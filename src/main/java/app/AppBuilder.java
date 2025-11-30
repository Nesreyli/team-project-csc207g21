package app;

import DataAccess.*;
import Entity.UserFactory;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.addToWatchlist.AddToWatchlistController;
import InterfaceAdapter.addToWatchlist.AddToWatchlistPresenter;
import InterfaceAdapter.addToWatchlist.AddToWatchlistViewModel;
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
import InterfaceAdapter.removeFromWatchlist.RemoveFromWatchlistController;
import InterfaceAdapter.removeFromWatchlist.RemoveFromWatchlistPresenter;
import InterfaceAdapter.removeFromWatchlist.RemoveFromWatchlistViewModel;
import InterfaceAdapter.signup.SignupController;
import InterfaceAdapter.signup.SignupPresenter;
import InterfaceAdapter.signup.SignupViewModel;
import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PricePresenter;
import InterfaceAdapter.stock_price.PriceViewModel;
import InterfaceAdapter.watchlist.WatchlistController;
import InterfaceAdapter.watchlist.WatchlistPresenter;
import InterfaceAdapter.watchlist.WatchlistViewModel;
import UseCase.Login.LoginInputBoundary;
import UseCase.Login.LogInInteractor;
import UseCase.Login.LoginOutputBoundary;
import UseCase.addToWatchlist.AddToWatchlistInputBoundary;
import UseCase.addToWatchlist.AddToWatchlistInteractor;
import UseCase.addToWatchlist.AddToWatchlistOutputBoundary;
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
import UseCase.removeFromWatchlist.RemoveFromWatchlistInputBoundary;
import UseCase.removeFromWatchlist.RemoveFromWatchlistInteractor;
import UseCase.removeFromWatchlist.RemoveFromWatchlistOutputBoundary;
import UseCase.signup.SignupInputBoundary;
import UseCase.signup.SignupInteractor;
import UseCase.signup.SignupOutputBoundary;
import UseCase.stock_price.PriceInputBoundary;
import UseCase.stock_price.PriceOutputBoundary;
import UseCase.stock_price.StockPriceInteractor;
import UseCase.watchlist.WatchlistInputBoundary;
import UseCase.watchlist.WatchlistInteractor;
import UseCase.watchlist.WatchlistOutputBoundary;
import UseCase.Stock_Search.SearchInputBoundary;
import UseCase.Stock_Search.SearchInteractor;
import UseCase.Stock_Search.SearchOutputBoundary;
import View.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);


    final UserDataAccessObject userDataAccessObject = new UserDataAccessObject(userFactory);
    final PortfolioAccessObject portfolioAccessObject = new PortfolioAccessObject();
    final NewsAccessObject newsAccessObject = new NewsAccessObject();
    final SearchAccessObject searchDataAccess = new SearchAccessObject();
    final PriceAccessObject priceAccessObject = new PriceAccessObject();
    final WatchlistAccessObject watchlistAccessObject = new WatchlistAccessObject();

    private AddToWatchlistViewModel addToWatchlistViewModel;
    private AddToWatchlistController addToWatchlistController;

    private RemoveFromWatchlistViewModel removeFromWatchlistViewModel;
    private RemoveFromWatchlistController removeFromWatchlistController;

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
    private WatchlistViewModel watchlistViewModel;
    private WatchlistView watchlistView;


    private WatchlistController watchlistController;

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
        newsViewModel = new NewsViewModel();
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel, newsViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addPortfolioView() {
        portViewModel = new PortfolioViewModel();
        portView = new PortfolioView(portViewModel);
        cardPanel.add(portView, portViewModel.getViewName());
        return this;
    }

    public AppBuilder addWatchlistView() {
        watchlistViewModel = new WatchlistViewModel();
        watchlistView = new WatchlistView(watchlistViewModel);
        cardPanel.add(watchlistView, watchlistViewModel.getViewName());
        return this;
    }

    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupViewModel.getViewName());
        return this;
    }
    public AppBuilder addPriceView() {
        priceViewModel = new PriceViewModel();
        stockPriceView = new StockPriceView(
                priceViewModel,
                addToWatchlistViewModel,
                addToWatchlistController,
                removeFromWatchlistViewModel,
                removeFromWatchlistController,
                watchlistViewModel,
                loggedInViewModel,
                watchlistController
        );
        stockPriceView.setLocationRelativeTo(searchView);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LogInInteractor(userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addPortfolioUseCase() {
        final PortfolioOutputBoundary portfolioOutputBoundary = new PortfolioPresenter(viewManagerModel,
                portViewModel, loggedInViewModel);
        final PortfolioInputBoundary portfolioInputBoundary = new PortfolioInteractor(portfolioAccessObject,
                portfolioOutputBoundary);
        PortfolioController portfolioController = new PortfolioController(portfolioInputBoundary);
        loggedInView.setPortfolioController(portfolioController);
        return this;
    }

    public AppBuilder addWatchlistUseCase() {
        final WatchlistOutputBoundary watchlistOutputBoundary = new WatchlistPresenter(viewManagerModel,
                watchlistViewModel, loggedInViewModel);
        final WatchlistInputBoundary watchlistInputBoundary = new WatchlistInteractor(watchlistAccessObject,
                watchlistOutputBoundary);
        watchlistController = new WatchlistController(watchlistInputBoundary);
        loggedInView.setWatchlistController(watchlistController);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel,
                viewManagerModel, loggedInViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(searchDataAccess, searchOutputBoundary);
        final HomeOutputBoundary homeOutputBoundary = (HomeOutputBoundary) searchOutputBoundary; // Optional cast
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);
        SearchController searchController = new SearchController(searchInteractor, homeInputBoundary);
        searchView.setSearchController(searchController);
        return this;
    }

    public AppBuilder addPriceUseCase() {
        final PriceOutputBoundary priceOutputBoundary = new PricePresenter(viewManagerModel,
                searchViewModel, priceViewModel);
        final PriceInputBoundary priceInputBoundary = new StockPriceInteractor(priceAccessObject, priceOutputBoundary);
        PriceController priceController = new PriceController(priceInputBoundary);
        searchView.setPriceController(priceController);
        return this;
    }

    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(portViewModel,
                viewManagerModel, loggedInViewModel);
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);
        HomeController homeController = new HomeController(homeInputBoundary);
        portView.setHomeController(homeController);
        watchlistView.setHomeController(homeController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LogoutInputBoundary logoutInputBoundary = new LogoutInteractor(logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInputBoundary);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addLoggedInUseCase() {
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(loggedInViewModel,
                viewManagerModel, searchViewModel);
        final LoggedInInputBoundary loggedInInputBoundary = new LoggedInInteractor(loggedInOutputBoundary);
        final LoggedInController loggedInController = new LoggedInController(loggedInInputBoundary);
        loggedInView.setLoggedInController(loggedInController);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary signupInputBoundary = new SignupInteractor(userDataAccessObject,
                signupOutputBoundary);
        final SignupController signupController = new SignupController(signupInputBoundary);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addAddToWatchlistUseCase() {
        addToWatchlistViewModel = new AddToWatchlistViewModel();
        AddToWatchlistOutputBoundary addOutput = new AddToWatchlistPresenter(addToWatchlistViewModel, watchlistViewModel);
        AddToWatchlistInputBoundary addInput = new AddToWatchlistInteractor(watchlistAccessObject, addOutput);
        addToWatchlistController = new AddToWatchlistController(addInput);
        return this;
    }

    public AppBuilder addRemoveFromWatchlistUseCase() {
        removeFromWatchlistViewModel = new RemoveFromWatchlistViewModel();
        RemoveFromWatchlistOutputBoundary removeOutput = new RemoveFromWatchlistPresenter(removeFromWatchlistViewModel, watchlistViewModel);
        RemoveFromWatchlistInputBoundary removeInput = new RemoveFromWatchlistInteractor(watchlistAccessObject, removeOutput);
        removeFromWatchlistController = new RemoveFromWatchlistController(removeInput);
        return this;
    }


    public AppBuilder addNewsUseCase() {
        final NewsOutputBoundary newsOutputBoundary = new NewsPresenter(viewManagerModel, newsViewModel);
        final NewsInputBoundary newsInputBoundary = new NewsInteractor(newsAccessObject, newsOutputBoundary);
        final NewsController newsController = new NewsController(newsInputBoundary);
        loggedInView.setNewsController(newsController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Panic Trade");
        application.setMinimumSize(new Dimension(600, 400));
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
