package View;

import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginState;
import InterfaceAdapter.logout.LogoutController;
import InterfaceAdapter.portfolio.PortfolioController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private LogoutController logoutController;

    private final JLabel username;

    private final JButton portfolio;
//    private final JPanel image;
    private PortfolioController portfolioController;

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
        portfolio = new JButton("Portfolio");
        buttons.add(portfolio);
        buttons.setBackground(Color.LIGHT_GRAY);
//        image = new JPanel();

//        changePassword = new JButton("Change Password");
//        buttons.add(changePassword);
//
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
//
//        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final LoggedInState currentState = loggedInViewModel.getState();
//                currentState.setPassword(passwordInputField.getText());
//                loggedInViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
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
}
