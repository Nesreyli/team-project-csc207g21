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
import view.theme.*;

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
     * @param loggedInViewModel The LoggedInViewModel that provides log in updates.
     * @param newsViewModel The NewsViewModel that provides news updates.
     * @param watchlistViewModel The WatchlistViewModel that provides watchlist updates.
     */

    public LoggedInView(LoggedInViewModel loggedInViewModel,
                        NewsViewModel newsViewModel,
                        WatchlistViewModel watchlistViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.newsViewModel = newsViewModel;
        this.watchlistViewModel = watchlistViewModel;

        this.loggedInViewModel.addPropertyChangeListener(this);

        setBackground(UiTheme.BG);
        setLayout(new GridBagLayout());

        initUi();
    }

    /**
     * Initializes and lays out all UI components inside the dashboard card.
     */
    private void initUi() {

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        final JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UiTheme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        final JLabel title = new JLabel("Welcome Back,");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 16));
        title.setForeground(UiTheme.TEXT_DARK);

        usernameLabel = new JLabel();
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 28));
        usernameLabel.setForeground(UiTheme.TEXT_DARK);

        final JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(UiTheme.CARD_BG);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(usernameLabel);
        card.add(header);

        card.add(Box.createVerticalStrut(25));

        final JPanel navPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        navPanel.setBackground(UiTheme.CARD_BG);

        portfolioButton = StyledButton.createPrimaryButton("Portfolio");
        watchlistButton = StyledButton.createPrimaryButton("Watchlist");
        stockSearchButton = StyledButton.createPrimaryButton("Stock Search");
        leaderboardButton = StyledButton.createPrimaryButton("Leaderboard");
        logoutButton = StyledButton.createOutlineButton("Logout");

        navPanel.add(portfolioButton);
        navPanel.add(watchlistButton);
        navPanel.add(stockSearchButton);
        navPanel.add(leaderboardButton);
        navPanel.add(logoutButton);

        addHeaderListeners();
        card.add(navPanel);

        card.add(Box.createVerticalStrut(30));

        final JLabel newsLabel = new JLabel("📰 Latest Market News");
        newsLabel.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 18));
        newsLabel.setForeground(UiTheme.TEXT_DARK);

        newsPanel = new NewsPanel(newsViewModel);
        newsPanel.setMinimumSize(new Dimension(400, 200));
        newsPanel.setPreferredSize(new Dimension(400, 200));

        card.add(newsLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(newsPanel);
        card.add(Box.createVerticalStrut(20));

        final JLabel watchlistLabel = new JLabel("📋 Your Watchlist");
        watchlistLabel.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 18));
        watchlistLabel.setForeground(UiTheme.TEXT_DARK);

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
            final LoggedInState state = loggedInViewModel.getState();
            portfolioController.portExecute(state.getUsername(), state.getPassword());
        });

        watchlistButton.addActionListener(e -> {
            final LoggedInState state = loggedInViewModel.getState();
            watchlistController.openWatchlist(state.getUsername(), state.getPassword());
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
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            usernameLabel.setText(state.getUsername());

            newsController.execute();
            watchlistController.fetchSilently(state.getUsername(), state.getPassword());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setPortfolioController(PortfolioController portfolioController) {
        this.portfolioController = portfolioController;
    }

    public void setWatchlistController(WatchlistController watchlistController) {
        this.watchlistController = watchlistController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setNewsController(NewsController newsController) {
        this.newsController = newsController;
    }

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }

    public void setLeaderboardController(LeaderboardController leaderboardController) {
        this.leaderboardController = leaderboardController;
    }

    public WatchlistPreviewPanel getWatchlistPreviewPanel() {
        return watchlistPanel;
    }
}
