package app;

import data_access.*;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_watchlist.AddToWatchlistController;
import interface_adapter.add_watchlist.AddToWatchlistPresenter;
import interface_adapter.add_watchlist.AddToWatchlistViewModel;
import interface_adapter.buySell.BuySellController;
import interface_adapter.buySell.BuySellPresenter;
import interface_adapter.buySell.BuySellViewModel;
import interface_adapter.homebutton.HomeController;
import interface_adapter.homebutton.HomePresenter;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsPresenter;
import interface_adapter.news.NewsViewModel;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.Stock_Search.SearchController;
import interface_adapter.Stock_Search.SearchPresenter;
import interface_adapter.Stock_Search.SearchViewModel;
import interface_adapter.remove_watchlist.RemoveFromWatchlistController;
import interface_adapter.remove_watchlist.RemoveFromWatchlistPresenter;
import interface_adapter.remove_watchlist.RemoveFromWatchlistViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.stock_price.PriceController;
import interface_adapter.stock_price.PricePresenter;
import interface_adapter.stock_price.PriceViewModel;
import interface_adapter.watchlist.WatchlistController;
import interface_adapter.watchlist.WatchlistPresenter;
import interface_adapter.watchlist.WatchlistViewModel;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardPresenter;
import interface_adapter.leaderboard.LeaderboardViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LogInInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.add_watchlist.AddToWatchlistInputBoundary;
import use_case.add_watchlist.AddToWatchlistInteractor;
import use_case.add_watchlist.AddToWatchlistOutputBoundary;
import use_case.buySell.BuySellInputBoundary;
import use_case.buySell.BuySellInteractor;
import use_case.buySell.BuySellOutputBoundary;
import use_case.homebutton.HomeInputBoundary;
import use_case.homebutton.HomeInteractor;
import use_case.homebutton.HomeOutputBoundary;
import use_case.loggedIn.LoggedInInputBoundary;
import use_case.loggedIn.LoggedInInteractor;
import use_case.loggedIn.LoggedInOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.news.NewsInputBoundary;
import use_case.news.NewsInteractor;
import use_case.news.NewsOutputBoundary;
import use_case.leaderboard.LeaderboardInputBoundary;
import use_case.leaderboard.LeaderboardInteractor;
import use_case.leaderboard.LeaderboardOutputBoundary;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInteractor;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistInputBoundary;
import use_case.remove_watchlist.RemoveFromWatchlistInteractor;
import use_case.remove_watchlist.RemoveFromWatchlistOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.stock_price.PriceInputBoundary;
import use_case.stock_price.PriceOutputBoundary;
import use_case.stock_price.StockPriceInteractor;
import use_case.watchlist.WatchlistInputBoundary;
import use_case.watchlist.WatchlistInteractor;
import use_case.watchlist.WatchlistOutputBoundary;
import use_case.stock_search.SearchInputBoundary;
import use_case.stock_search.SearchInteractor;
import use_case.stock_search.SearchOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

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
    final LeaderboardAccessObject leaderboardAccessObject = new LeaderboardAccessObject();
    final BuySellAccessObject buySellAccessObject = new BuySellAccessObject();

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
    private ReceiptDialog receiptDialog;
    private BuySellViewModel buySellViewModel;

    private LeaderboardViewModel leaderboardViewModel;
    private LeaderboardView leaderboardView;

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

    public AppBuilder addLeaderboardView() {
        leaderboardViewModel = new LeaderboardViewModel();
        leaderboardView = new LeaderboardView(leaderboardViewModel, loggedInViewModel);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
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
        if (leaderboardView != null) {
            leaderboardView.setHomeController(homeController);
        }
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

    public AppBuilder addBuySellView(){
        buySellViewModel = new BuySellViewModel();
        receiptDialog = new ReceiptDialog(stockPriceView, buySellViewModel);
        return this;
    }
    public AppBuilder addBuySellUseCase() {
        final BuySellOutputBoundary bsOutputBoundary = new BuySellPresenter(buySellViewModel, viewManagerModel);
        final BuySellInputBoundary bsInputBoundary = new BuySellInteractor(buySellAccessObject, bsOutputBoundary);
        final BuySellController bsController = new BuySellController(bsInputBoundary);
        stockPriceView.setBuySellController(bsController);
        return this;
    }

    public AppBuilder addLeaderboardUseCase() {
        final LeaderboardOutputBoundary leaderboardOutputBoundary = new LeaderboardPresenter(viewManagerModel, leaderboardViewModel);
        final LeaderboardInputBoundary leaderboardInputBoundary = new LeaderboardInteractor(leaderboardAccessObject, leaderboardOutputBoundary);
        final LeaderboardController leaderboardController = new LeaderboardController(leaderboardInputBoundary);
        loggedInView.setLeaderboardController(leaderboardController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Panic Trade");
        application.setMinimumSize(new Dimension(600, 400));
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Set application icon
        try {
            ImageIcon icon = null;
            String[] possiblePaths = {
                "src/image/panictraderpic.png",
                "image/panictraderpic.png"
            };
            
            // Try file paths first
            for (String path : possiblePaths) {
                java.io.File iconFile = new java.io.File(path);
                if (iconFile.exists() && iconFile.isFile()) {
                    icon = new ImageIcon(iconFile.getAbsolutePath());
                    if (icon.getIconWidth() > 0) { // Verify image loaded
                        break;
                    }
                    icon = null;
                }
            }
            
            // Try as resource if file path didn't work
            if (icon == null) {
                java.net.URL imageURL = AppBuilder.class.getResource("/image/panictraderpic.png");
                if (imageURL == null) {
                    imageURL = AppBuilder.class.getClassLoader().getResource("image/panictraderpic.png");
                }
                if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                }
            }
            
            if (icon != null && icon.getIconWidth() > 0) {
                // Scale the image to a larger size for better visibility
                java.awt.Image originalImage = icon.getImage();
                int targetSize = 128; // Larger size for better visibility
                java.awt.Image scaledImage = originalImage.getScaledInstance(
                    targetSize, targetSize, java.awt.Image.SCALE_SMOOTH);
                application.setIconImage(scaledImage);
            } else {
                System.err.println("App icon not found or could not be loaded. Tried: " + String.join(", ", possiblePaths));
            }
        } catch (Exception e) {
            // Icon not found, continue without it
            System.err.println("Could not load app icon: " + e.getMessage());
        }
        
        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
