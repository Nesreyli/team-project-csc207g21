package View;

import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.usersession.UserSessionViewModel;
import UI.UIColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardPanel extends JPanel implements PropertyChangeListener {

    private final JTabbedPane tabs = new JTabbedPane();
    private final JLabel usernameLabel;

    // Controllers (given from factories)
    private final PortfolioController portfolioController;
    private final LogoutController logoutController;
/*    private final StockInfoController stockInfoController;
    private final BuyStockController buyController;
    private final SellStockController sellController;
    private final WatchlistController watchlistController;
    private final TransactionHistoryController historyController;
    private final LeaderboardController leaderboardController;
    private final NewsController newsController;*/

    // ViewModels
    private final UserSessionViewModel userSessionViewModel;
    private final PortfolioViewModel portfolioViewModel;
   /* private final StockInfoViewModel stockInfoViewModel;
    private final WatchlistViewModel watchlistViewModel;
    private final TransactionHistoryViewModel transactionHistoryViewModel;
    private final LeaderboardViewModel leaderboardViewModel;
    private final NewsViewModel newsViewModel;*/

    public DashboardPanel(
            // Controllers injected from factories
            PortfolioController portfolioController,
            LogoutController logoutController,
//            StockInfoController stockInfoController,
//            BuyStockController buyController,
//            SellStockController sellController,
//            WatchlistController watchlistController,
//            TransactionHistoryController historyController,
//            LeaderboardController leaderboardController,
//            NewsController newsController,

            // ViewModels injected from presenters
            UserSessionViewModel userSessionViewModel,
            PortfolioViewModel portfolioViewModel
//            StockInfoViewModel stockInfoViewModel,
//            WatchlistViewModel watchlistViewModel,
//            TransactionHistoryViewModel transactionHistoryViewModel,
//            LeaderboardViewModel leaderboardViewModel,
//            NewsViewModel newsViewModel
    ) {

        this.portfolioController = portfolioController;
        this.logoutController = logoutController;
        // this.stockInfoController = stockInfoController;
        // this.buyController = buyController;
        /*this.sellController = sellController;
        this.watchlistController = watchlistController;
        this.historyController = historyController;
        this.leaderboardController = leaderboardController;
        this.newsController = newsController;*/

        this.userSessionViewModel = userSessionViewModel;
        userSessionViewModel.addPropertyChangeListener(this);
        this.portfolioViewModel = portfolioViewModel;
       /* this.stockInfoViewModel = stockInfoViewModel;
        this.watchlistViewModel = watchlistViewModel;
        this.transactionHistoryViewModel = transactionHistoryViewModel;
        this.leaderboardViewModel = leaderboardViewModel;
        this.newsViewModel = newsViewModel;*/

        setLayout(new BorderLayout());

        // Basic User Info
        final JLabel usernameInfo = new JLabel("Welcome, ");
        usernameInfo.setFont(new Font(usernameInfo.getFont().getFontName(), Font.PLAIN, 18));
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font(usernameInfo.getFont().deriveFont(Font.BOLD).getFontName(), Font.PLAIN, 24));

        JTextField searchTextField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        // BasicUserInfo Layout
        ImageIcon logOutIcon = new ImageIcon(
                getClass().getResource("/icons/logout.png")
        );
        JButton logOut = new JButton(logOutIcon);
        logOut.setBackground(Color.WHITE);
        logOut.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        logoutController.execute();
//                        if (evt.getSource().equals(logout)){
//
//                        }
                    }
                }
        );

        JPanel welcomePanel = new JPanel();
        welcomePanel.add(usernameInfo);
        welcomePanel.add(usernameLabel);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(UIColor.PANEL);
        welcomePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.add(logOut);
        userPanel.add(welcomePanel);
        userPanel.setBackground(UIColor.PANEL);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(userPanel, BorderLayout.SOUTH);
        leftPanel.setBackground(UIColor.PANEL);


        headerPanel.add(leftPanel);

        headerPanel.setBackground(UIColor.PANEL);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(headerPanel, BorderLayout.NORTH);
        setBackground(UIColor.BACKGROUND);

        createTabs();
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIColor.BACKGROUND);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabs, BorderLayout.CENTER);
        tabs.setBackground(UIColor.BACKGROUND);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);

        setBackground(UIColor.BACKGROUND);


        String username = userSessionViewModel.getState().username();
        String password = userSessionViewModel.getState().password();
        portfolioController.execute(username, password);

        usernameLabel.setText(username);
    }

    private void createTabs() {
        tabs.add("Portfolio", new PortfolioPanel(portfolioController, portfolioViewModel));
        // tabs.add("Search Stock", new SearchStockPanel(stockInfoController, buyController, sellController, stockInfoViewModel));
        // tabs.add("Watchlist", new WatchlistPanel(watchlistController, watchlistViewModel));
        // tabs.add("History", new TransactionHistoryPanel(historyController, transactionHistoryViewModel));
        // tabs.add("Performance", new PerformancePanel(portfolioViewModel));
        // tabs.add("Leaderboard", new LeaderboardPanel(leaderboardController, leaderboardViewModel));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(() -> {
                var state = (UserSessionViewModel.UserSessionState) evt.getNewValue();
                usernameLabel.setText(state.username());
            });
        }
    }
}
