package app;

import java.awt.*;

import javax.swing.*;

import data_access.*;
import entity.UserFactory;
import interface_adapter.Stock_Search.SearchController;
import interface_adapter.Stock_Search.SearchPresenter;
import interface_adapter.Stock_Search.SearchViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_watchlist.AddToWatchlistController;
import interface_adapter.add_watchlist.AddToWatchlistPresenter;
import interface_adapter.add_watchlist.AddToWatchlistViewModel;
import interface_adapter.buySell.BuySellController;
import interface_adapter.buySell.BuySellPresenter;
import interface_adapter.buySell.BuySellViewModel;
import interface_adapter.homebutton.HomeController;
import interface_adapter.homebutton.HomePresenter;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardPresenter;
import interface_adapter.leaderboard.LeaderboardViewModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsPresenter;
import interface_adapter.news.NewsViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.portfolio.PortfolioViewModel;
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
import use_case.add_watchlist.AddToWatchlistInputBoundary;
import use_case.add_watchlist.AddToWatchlistInteractor;
import use_case.add_watchlist.AddToWatchlistOutputBoundary;
import use_case.buySell.BuySellInputBoundary;
import use_case.buySell.BuySellInteractor;
import use_case.buySell.BuySellOutputBoundary;
import use_case.homebutton.HomeInputBoundary;
import use_case.homebutton.HomeInteractor;
import use_case.homebutton.HomeOutputBoundary;
import use_case.leaderboard.LeaderboardInputBoundary;
import use_case.leaderboard.LeaderboardInteractor;
import use_case.leaderboard.LeaderboardOutputBoundary;
import use_case.loggedIn.LoggedInInputBoundary;
import use_case.loggedIn.LoggedInInteractor;
import use_case.loggedIn.LoggedInOutputBoundary;
import use_case.login.LogInInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.news.NewsInputBoundary;
import use_case.news.NewsInteractor;
import use_case.news.NewsOutputBoundary;
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
import use_case.stock_search.SearchInputBoundary;
import use_case.stock_search.SearchInteractor;
import use_case.stock_search.SearchOutputBoundary;
import use_case.watchlist.WatchlistInputBoundary;
import use_case.watchlist.WatchlistInteractor;
import use_case.watchlist.WatchlistOutputBoundary;
import view.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new UserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final UserDataAccessObject userDataAccessObject = new UserDataAccessObject(userFactory);
    private final PortfolioAccessObject portfolioAccessObject = new PortfolioAccessObject();
    private final NewsAccessObject newsAccessObject = new NewsAccessObject();
    private final SearchAccessObject searchDataAccess = new SearchAccessObject();
    private final PriceAccessObject priceAccessObject = new PriceAccessObject();
    private final WatchlistAccessObject watchlistAccessObject = new WatchlistAccessObject();
    private final LeaderboardAccessObject leaderboardAccessObject = new LeaderboardAccessObject();
    private final BuySellAccessObject buySellAccessObject = new BuySellAccessObject();

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

    /**
     * Adds the login view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the logged-in view, including the news view model, to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLoggedInView() {
        newsViewModel = new NewsViewModel();
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel, newsViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the portfolio view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addPortfolioView() {
        portViewModel = new PortfolioViewModel();
        portView = new PortfolioView(portViewModel);
        cardPanel.add(portView, portViewModel.getViewName());
        return this;
    }

    /**
     * Adds the watchlist view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addWatchlistView() {
        watchlistViewModel = new WatchlistViewModel();
        removeFromWatchlistViewModel = new RemoveFromWatchlistViewModel();

        final RemoveFromWatchlistOutputBoundary removeOutput =
                new RemoveFromWatchlistPresenter(removeFromWatchlistViewModel, watchlistViewModel);
        final RemoveFromWatchlistInputBoundary removeInput =
                new RemoveFromWatchlistInteractor(watchlistAccessObject, removeOutput);
        removeFromWatchlistController = new RemoveFromWatchlistController(removeInput);

        watchlistView = new WatchlistView(watchlistViewModel, removeFromWatchlistController);
        cardPanel.add(watchlistView, watchlistView.getViewName());

        return this;
    }

    /**
     * Adds the search view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    /**
     * Adds the signup view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupViewModel.getViewName());
        return this;
    }

    /**
     * Adds the leaderboard view to the application.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLeaderboardView() {
        leaderboardViewModel = new LeaderboardViewModel();
        leaderboardView = new LeaderboardView(leaderboardViewModel, loggedInViewModel);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
        return this;
    }

    /**
     * Adds the stock price view to the application.
     * @return the current AppBuilder instance
     */
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

    /**
     * Adds the login use case to the application.
     * Sets up the interactor, presenter, and controller for login functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LogInInteractor(userDataAccessObject, loginOutputBoundary);
        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the portfolio use case to the application.
     * Sets up the interactor, presenter, and controller for portfolio functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addPortfolioUseCase() {
        final PortfolioOutputBoundary portfolioOutputBoundary = new PortfolioPresenter(viewManagerModel,
                portViewModel, loggedInViewModel);
        final PortfolioInputBoundary portfolioInputBoundary = new PortfolioInteractor(portfolioAccessObject,
                priceAccessObject, portfolioOutputBoundary);
        final PortfolioController portfolioController = new PortfolioController(portfolioInputBoundary);
        loggedInView.setPortfolioController(portfolioController);
        return this;
    }

    /**
     * Adds the watchlist use case to the application.
     * Sets up the interactor, presenter, and controller for watchlist functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addWatchlistUseCase() {
        final WatchlistOutputBoundary watchlistOutputBoundary = new WatchlistPresenter(viewManagerModel,
                watchlistViewModel, loggedInViewModel);
        final WatchlistInputBoundary watchlistInputBoundary = new WatchlistInteractor(watchlistAccessObject,
                watchlistOutputBoundary);
        watchlistController = new WatchlistController(watchlistInputBoundary);
        loggedInView.setWatchlistController(watchlistController);
        return this;
    }

    /**
     * Adds the search use case to the application.
     * Sets up the interactor, presenter, and controller for search functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel,
                viewManagerModel, loggedInViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(searchDataAccess, searchOutputBoundary);
        final HomeOutputBoundary homeOutputBoundary = (HomeOutputBoundary) searchOutputBoundary;
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);
        final SearchController searchController = new SearchController(searchInteractor, homeInputBoundary);
        searchView.setSearchController(searchController);
        return this;
    }

    /**
     * Adds the stock price use case to the application.
     * Sets up the interactor, presenter, and controller for stock price functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addPriceUseCase() {
        final PriceOutputBoundary priceOutputBoundary = new PricePresenter(viewManagerModel,
                searchViewModel, priceViewModel);
        final PriceInputBoundary priceInputBoundary = new StockPriceInteractor(priceAccessObject, priceOutputBoundary);
        final PriceController priceController = new PriceController(priceInputBoundary);
        searchView.setPriceController(priceController);
        return this;
    }

    /**
     * Adds the home navigation use case to the application.
     * Sets up the interactor, presenter, and controller for home button navigation.
     * @return the current AppBuilder instance
     */
    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(portViewModel,
                viewManagerModel, loggedInViewModel);
        final HomeInputBoundary homeInputBoundary = new HomeInteractor(homeOutputBoundary);
        final HomeController homeController = new HomeController(homeInputBoundary);
        portView.setHomeController(homeController);
        watchlistView.setHomeController(homeController);
        if (leaderboardView != null) {
            leaderboardView.setHomeController(homeController);
        }
        return this;
    }

    /**
     * Adds the logout use case to the application.
     * Sets up the interactor, presenter, and controller for logout functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LogoutInputBoundary logoutInputBoundary = new LogoutInteractor(logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInputBoundary);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the logged-in use case to the application.
     * Sets up the interactor, presenter, and controller for logged-in view functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLoggedInUseCase() {
        final LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(loggedInViewModel,
                viewManagerModel, searchViewModel);
        final LoggedInInputBoundary loggedInInputBoundary = new LoggedInInteractor(loggedInOutputBoundary);
        final LoggedInController loggedInController = new LoggedInController(loggedInInputBoundary);
        loggedInView.setLoggedInController(loggedInController);
        return this;
    }

    /**
     * Adds the signup use case to the application.
     * Sets up the interactor, presenter, and controller for signup functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary signupInputBoundary = new SignupInteractor(userDataAccessObject,
                signupOutputBoundary);
        final SignupController signupController = new SignupController(signupInputBoundary);
        signupView.setSignupController(signupController);
        return this;
    }

    /**
     * Adds the "Add to Watchlist" use case to the application.
     * Sets up the interactor, presenter, and controller for adding stocks to the watchlist.
     * @return the current AppBuilder instance
     */
    public AppBuilder addAddToWatchlistUseCase() {
        addToWatchlistViewModel = new AddToWatchlistViewModel();
        final AddToWatchlistOutputBoundary addOutput = new AddToWatchlistPresenter(addToWatchlistViewModel,
                watchlistViewModel);
        final AddToWatchlistInputBoundary addInput = new AddToWatchlistInteractor(watchlistAccessObject, addOutput);
        addToWatchlistController = new AddToWatchlistController(addInput);
        return this;
    }

    /**
     * Adds the "Remove from Watchlist" use case to the application.
     * Sets up the interactor, presenter, and controller for removing stocks from the watchlist.
     * @return the current AppBuilder instance
     */
    public AppBuilder addRemoveFromWatchlistUseCase() {
        removeFromWatchlistViewModel = new RemoveFromWatchlistViewModel();
        final RemoveFromWatchlistOutputBoundary removeOutput = new RemoveFromWatchlistPresenter(
                        removeFromWatchlistViewModel, watchlistViewModel);
        final RemoveFromWatchlistInputBoundary removeInput = new RemoveFromWatchlistInteractor(watchlistAccessObject,
                removeOutput);
        removeFromWatchlistController = new RemoveFromWatchlistController(removeInput);
        return this;
    }

    /**
     * Adds the news use case to the application.
     * Sets up the interactor, presenter, and controller for news functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addNewsUseCase() {
        final NewsOutputBoundary newsOutputBoundary = new NewsPresenter(viewManagerModel, newsViewModel);
        final NewsInputBoundary newsInputBoundary = new NewsInteractor(newsAccessObject, newsOutputBoundary);
        final NewsController newsController = new NewsController(newsInputBoundary);
        loggedInView.setNewsController(newsController);
        return this;
    }

    /**
     * Adds the Buy/Sell view to the application.
     * Initializes the receipt dialog for trading operations.
     * @return the current AppBuilder instance
     */
    public AppBuilder addBuySellView() {
        buySellViewModel = new BuySellViewModel();
        receiptDialog = new ReceiptDialog(stockPriceView, buySellViewModel);
        return this;
    }

    /**
     * Adds the Buy/Sell use case to the application.
     * Sets up the interactor, presenter, and controller for buying and selling stocks.
     * @return the current AppBuilder instance
     */
    public AppBuilder addBuySellUseCase() {
        final BuySellOutputBoundary bsOutputBoundary = new BuySellPresenter(buySellViewModel, viewManagerModel);
        final BuySellInputBoundary bsInputBoundary = new BuySellInteractor(buySellAccessObject, bsOutputBoundary);
        final BuySellController bsController = new BuySellController(bsInputBoundary);
        stockPriceView.setBuySellController(bsController);
        return this;
    }

    /**
     * Adds the leaderboard use case to the application.
     * Sets up the interactor, presenter, and controller for leaderboard functionality.
     * @return the current AppBuilder instance
     */
    public AppBuilder addLeaderboardUseCase() {
        final LeaderboardOutputBoundary leaderboardOutputBoundary = new LeaderboardPresenter(viewManagerModel,
                leaderboardViewModel);
        final LeaderboardInputBoundary leaderboardInputBoundary = new LeaderboardInteractor(leaderboardAccessObject,
                leaderboardOutputBoundary);
        final LeaderboardController leaderboardController = new LeaderboardController(leaderboardInputBoundary);
        loggedInView.setLeaderboardController(leaderboardController);
        return this;
    }

    /**
     * Builds the JFrame for the application, sets minimum size and default close operation,
     * adds the card panel containing all views, and initializes the view manager model.
     * @return the constructed  JFrame instance
     */
    public JFrame build() {
        final JFrame application = new JFrame("Panic Trade");
        application.setMinimumSize(new Dimension(600, 400));
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Set application icon
        try {
            ImageIcon icon = null;
            final String[] possiblePaths = {
                "src/image/panictraderpic.png",
                "image/panictraderpic.png",
            };
            
            // Try file paths first
            for (String path : possiblePaths) {
                final java.io.File iconFile = new java.io.File(path);
                if (iconFile.exists() && iconFile.isFile()) {
                    icon = new ImageIcon(iconFile.getAbsolutePath());
                    if (icon.getIconWidth() > 0) {
                        break;
                    }
                    icon = null;
                }
            }
            
            // Try as resource if file path didn't work
            if (icon == null) {
                java.net.URL imageUrl = AppBuilder.class.getResource("/image/panictraderpic.png");
                if (imageUrl == null) {
                    imageUrl = AppBuilder.class.getClassLoader().getResource("image/panictraderpic.png");
                }
                if (imageUrl != null) {
                    icon = new ImageIcon(imageUrl);
                }
            }
            
            if (icon != null && icon.getIconWidth() > 0) {
                // Scale the image to a larger size for better visibility
                final java.awt.Image originalImage = icon.getImage();
                // Larger size for better visibility
                final int targetSize = 128;
                java.awt.Image scaledImage = originalImage.getScaledInstance(
                    targetSize, targetSize, java.awt.Image.SCALE_SMOOTH);
                application.setIconImage(scaledImage);
            }
            else {
                System.err.println("App icon not found or could not be loaded. Tried: "
                        + String.join(", ", possiblePaths));
            }
        }
        catch (Exception error) {
            // Icon not found, continue without it
            System.err.println("Could not load app icon: " + error.getMessage());
        }
        
        application.add(cardPanel);

        viewManagerModel.setState(loginView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
