package View;

import Entity.News;
import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInController;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.news.NewsController;
import InterfaceAdapter.news.NewsViewModel;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.watchlist.WatchlistController;
import InterfaceAdapter.leaderboard.LeaderboardController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();

    private final JButton watchlist;
    private WatchlistController watchlistController;

    private final JLabel username;
    private final JButton logout;
    private final JButton portfolio;
//    private final JPanel image;
    private PortfolioController portfolioController;
    private LogoutController logoutController;
    private NewsController newsController;
    private LeaderboardController leaderboardController;

    private final JButton stockSearch;
    private final JButton leaderboard;
//    private final JPanel image;
    private LoggedInController loggedInController;
    private NewsViewModel newsViewModel;
    private NewsPanel newsPanel;

//    private final JTextField passwordInputField = new JTextField(15);
//    private final JButton changePassword;

    public LoggedInView(LoggedInViewModel loggedInViewModel, NewsViewModel newsViewModel) {
        Color bright = new Color(214, 216, 220);
        Color dark = new Color(43, 45, 48);
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.newsViewModel = newsViewModel;

        newsPanel = new NewsPanel(newsViewModel);

//        final LabelTextPanel passwordInfo = new LabelTextPanel(
//                new JLabel("Password"), passwordInputField);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        usernameInfo.setForeground(bright);
        username = new JLabel();
        username.setBackground(dark);
        username.setForeground(bright);
        username.setFont(new Font("Arial", Font.BOLD, 14));

        final JPanel buttons = new JPanel();
        logout = new JButton("Logout");
        logout.setForeground(dark);
        buttons.add(logout);
        logout.addActionListener(
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

        portfolio = new JButton("Portfolio");

        portfolio.setForeground(dark);
        buttons.add(portfolio);
        portfolio.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(portfolio)) {
                    final LoggedInState currentState = loggedInViewModel.getState();

                    portfolioController.portExecute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                }
            }
        });

        watchlist = new JButton("Watchlist");
        watchlist.setForeground(dark);
        buttons.add(watchlist);
        watchlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(watchlist)) {
                    final LoggedInState currentState = loggedInViewModel.getState();

                    watchlistController.execute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                }
            }
        });
      
        stockSearch = new JButton("Stock Search");
        stockSearch.setForeground(dark);
        buttons.add(stockSearch);
      
        stockSearch.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToSearch();
                    }
                });

        leaderboard = new JButton("Leaderboard");
        leaderboard.setForeground(dark);
        buttons.add(leaderboard);

        leaderboard.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(leaderboard)) {
                            leaderboardController.execute();
                        }
                    }
                });

        buttons.setBackground(dark);

        this.setAlignmentX(1.0f);

        JPanel user = new JPanel();
        user.add(usernameInfo);
        user.add(username);

        user.setLayout(new FlowLayout(FlowLayout.LEFT));
        user.setBackground(dark);
        user.add(buttons, BorderLayout.CENTER);

        this.setBackground(dark);
        this.add(user);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(newsPanel);
//        JLabel pic = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/buffet1.png")));
//        pic.setPreferredSize(new Dimension(130,100));
//        image.add(pic);
//        this.add(image);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
            logoutController.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
            newsController.execute();
        }
        // for multiple porpery change
//        else if (evt.getPropertyName().equals("password")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            if (state.getPasswordError() == null) {
//                JOptionPane.showMessageDialog(this, "password updated for " + state.getUsername());
//                passwordInputField.setText("");
//            }
//            else {
//                JOptionPane.showMessageDialog(this, state.getPasswordError());
//            }
//        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setPortfolioController(PortfolioController portfolioController) {
        this.portfolioController = portfolioController;
    }

    public void setWatchlistController(WatchlistController controller){
        this.watchlistController = controller;
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
}
