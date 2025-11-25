package View;

import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginState;
import InterfaceAdapter.login.LoginViewModel;
import InterfaceAdapter.usersession.UserSessionViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the login screen.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Login";
    private final LoginViewModel loginViewModel;
    private final UserSessionViewModel userSessionViewModel;

    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel, UserSessionViewModel userSessionViewModel) {
        this.loginViewModel = loginViewModel;
        this.userSessionViewModel = userSessionViewModel;

        this.loginViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(new JLabel("Username"), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(new JLabel("Password"), passwordInputField);

        JPanel buttons = new JPanel();
        logIn = new JButton("Log In");
        cancel = new JButton("Cancel");
        buttons.add(logIn);
        buttons.add(cancel);

        logIn.addActionListener(e -> {
            if (loginController != null) {
                LoginViewModel.LoginState state = loginViewModel.getState();
                loginController.execute(state.getUsername(), state.getPassword());
            }
        });

        cancel.addActionListener(this);

        addDocumentListeners();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        usernameErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        passwordErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    private void addDocumentListeners() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                LoginViewModel.LoginState state = loginViewModel.getState();
                state.setUsername(usernameInputField.getText());
                loginViewModel.setState(state);
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                LoginViewModel.LoginState state = loginViewModel.getState();
                state.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(state);
            }
            @Override public void insertUpdate(DocumentEvent e) { update(); }
            @Override public void removeUpdate(DocumentEvent e) { update(); }
            @Override public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        LoginViewModel.LoginState state = (LoginViewModel.LoginState) evt.getNewValue();
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
        usernameErrorField.setText(state.getLoginError());
        revalidate();
        repaint();
    }


    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
