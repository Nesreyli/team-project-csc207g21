package View;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.logged_in.LoggedInController;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.news.NewsController;
import InterfaceAdapter.portfolio.PortfolioController;

import javax.swing.*;
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

    private final JLabel username;
    private final JButton logout;
    private final JButton portfolio;
    private final JButton news;
//    private final JPanel image;
    private PortfolioController portfolioController;
    private LogoutController logoutController;
    private NewsController newsController;
    private final JButton stockSearch;
//    private final JPanel image;
    private LoggedInController loggedInController;

//    private final JTextField passwordInputField = new JTextField(15);
//    private final JButton changePassword;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);


//        final LabelTextPanel passwordInfo = new LabelTextPanel(
//                new JLabel("Password"), passwordInputField);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logout = new JButton("Logout");
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
      
        stockSearch = new JButton("Stock Search");
        buttons.add(stockSearch);
      
        stockSearch.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        loggedInController.switchToSearch();
                    }
                });      


        news = new JButton("Top News");
        buttons.add(news);
        news.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    newsController.execute();
                }
            }
        );
        buttons.setBackground(Color.LIGHT_GRAY);

        this.setAlignmentX(1.0f);

        JPanel user = new JPanel();
        user.add(usernameInfo);
        user.add(username);
        user.setLayout(new BoxLayout(user, BoxLayout.X_AXIS));
        user.setBackground(Color.LIGHT_GRAY);
        this.add(user);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buttons, BorderLayout.CENTER);
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

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setNewsController(NewsController newsController) {
        this.newsController = newsController;
      
    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }
}
