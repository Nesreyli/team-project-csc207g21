package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.watchlist.WatchlistController;
import interface_adapter.watchlist.WatchlistViewModel;

import static view.theme.StyledButton.createOutlineButton;
import static view.theme.StyledButton.createPrimaryButton;
import static view.theme.UITheme.*;

/**
 * LoggedInView represents the main dashboard screen after a user logs in.
 * It displays navigation buttons, a welcome header, the latest news panel,
 * and a preview of the user's watchlist. It listens to changes in the
 * LoggedInViewModel to update the username and trigger news/watchlist refresh.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";

    private final LoggedInViewModel loggedInViewModel;
    private final NewsViewModel newsViewModel;
    private final WatchlistViewModel watchlistViewModel;

    private JLabel usernameLabel;

    private JButton logoutButton;
    private JButton portfolioButton;
    private JButton watchlistButton;
    private JButton stockSearchButton;
    private JButton leaderboardButton;

    private NewsPanel newsPanel;
    private WatchlistPreviewPanel watchlistPanel;

    private PortfolioController portfolioController;
    private WatchlistController watchlistController;
    private LogoutController logoutController;
    private NewsController newsController;
    private LeaderboardController leaderboardController;
    private LoggedInController loggedInController;
    /**
     * Constructs the LoggedInView with required ViewModels.
     */
    public LoggedInView(LoggedInViewModel vm, NewsViewModel nvm, WatchlistViewModel wvm) {
        this.loggedInViewModel = vm;
        this.newsViewModel = nvm;
        this.watchlistViewModel = wvm;

        this.loggedInViewModel.addPropertyChangeListener(this);

        setBackground(BG);
        setLayout(new GridBagLayout());

        initUI();
    }

    /**
     * Initializes and lays out all UI components inside the dashboard card.
     */
    private void initUI() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Welcome Back,");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        title.setForeground(TEXT_DARK);

        usernameLabel = new JLabel();
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        usernameLabel.setForeground(TEXT_DARK);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(CARD_BG);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(usernameLabel);
        card.add(header);

        card.add(Box.createVerticalStrut(25));

        JPanel navPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        navPanel.setBackground(CARD_BG);

        portfolioButton = createPrimaryButton("Portfolio");
        watchlistButton = createPrimaryButton("Watchlist");
        stockSearchButton = createPrimaryButton("Stock Search");
        leaderboardButton = createPrimaryButton("Leaderboard");
        logoutButton = createOutlineButton("Logout");

        navPanel.add(portfolioButton);
        navPanel.add(watchlistButton);
        navPanel.add(stockSearchButton);
        navPanel.add(leaderboardButton);
        navPanel.add(logoutButton);

        addHeaderListeners();
        card.add(navPanel);

        card.add(Box.createVerticalStrut(30));

        JLabel newsLabel = new JLabel("📰 Latest Market News");
        newsLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        newsLabel.setForeground(TEXT_DARK);

        newsPanel = new NewsPanel(newsViewModel);
        newsPanel.setMinimumSize(new Dimension(400, 200));
        newsPanel.setPreferredSize(new Dimension(400, 200));

        card.add(newsLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(newsPanel);
        card.add(Box.createVerticalStrut(20));

        JLabel watchlistLabel = new JLabel("📋 Your Watchlist");
        watchlistLabel.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        watchlistLabel.setForeground(TEXT_DARK);

        watchlistPanel = new WatchlistPreviewPanel(watchlistViewModel);
        watchlistPanel.setMinimumSize(new Dimension(400, 200));
        watchlistPanel.setPreferredSize(new Dimension(400, 200));

        card.add(watchlistLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(watchlistPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    /**
     * Wires all navigation buttons to their respective controllers.
     */
    private void addHeaderListeners() {
        logoutButton.addActionListener(e -> logoutController.execute());

        portfolioButton.addActionListener(e -> {
            LoggedInState s = loggedInViewModel.getState();
            portfolioController.portExecute(s.getUsername(), s.getPassword());
        });

        watchlistButton.addActionListener(e -> {
            LoggedInState s = loggedInViewModel.getState();
            watchlistController.openWatchlist(s.getUsername(), s.getPassword());
        });

        stockSearchButton.addActionListener(e -> {
            loggedInController.switchToSearch();
        });


        leaderboardButton.addActionListener(e -> leaderboardController.execute());
    }

    /**
     * Responds to LoggedInViewModel state updates by refreshing
     * the displayed username and triggering news/watchlist updates.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            LoggedInState s = (LoggedInState) evt.getNewValue();
            usernameLabel.setText(s.getUsername());

            newsController.execute();
            watchlistController.fetchSilently(s.getUsername(), s.getPassword());
        }
    }

    public String getViewName() { return viewName; }
    public void setPortfolioController(PortfolioController c) { portfolioController = c; }
    public void setWatchlistController(WatchlistController c) { watchlistController = c; }
    public void setLogoutController(LogoutController c) { logoutController = c; }
    public void setNewsController(NewsController c) { newsController = c; }
    public void setLoggedInController(LoggedInController c) { loggedInController = c; }
    public void setLeaderboardController(LeaderboardController c) { leaderboardController = c; }

    public WatchlistPreviewPanel getWatchlistPreviewPanel() { return watchlistPanel; }
}
