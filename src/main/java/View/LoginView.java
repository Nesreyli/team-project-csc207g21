package View;

import InterfaceAdapter.login.LoginController;
import InterfaceAdapter.login.LoginState;
import InterfaceAdapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();

    private final JButton logIn;
    private LoginController loginController = null;
    private final JButton signup;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Set layout and background
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(240, 242, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create main card panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));

        // Logo/Icon area
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel logoLabel = new JLabel("📈");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoPanel.add(logoLabel);

        // Title
        JLabel title = new JLabel("Panic Trade");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(51, 51, 51));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to your account");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 120, 120));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field with custom styling
        JPanel usernamePanel = createStyledInputPanel("Username", usernameField, "👤");

        // Password field with custom styling
        JPanel passwordPanel = createStyledInputPanel("Password", passwordField, "🔒");

        // Error label
        errorLabel.setForeground(new Color(220, 53, 69));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        logIn = createStyledButton("Sign In", new Color(0, 123, 255), Color.WHITE);
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Divider
        JPanel dividerPanel = new JPanel();
        dividerPanel.setLayout(new GridBagLayout());
        dividerPanel.setBackground(Color.WHITE);
        dividerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        dividerPanel.setMaximumSize(new Dimension(400, 40));

        GridBagConstraints dividerGbc = new GridBagConstraints();
        dividerGbc.fill = GridBagConstraints.HORIZONTAL;
        dividerGbc.insets = new Insets(0, 5, 0, 5);

        JSeparator separator1 = new JSeparator();
        dividerGbc.gridx = 0;
        dividerGbc.weightx = 1.0;
        dividerPanel.add(separator1, dividerGbc);

        JLabel orLabel = new JLabel("or");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orLabel.setForeground(new Color(120, 120, 120));
        dividerGbc.gridx = 1;
        dividerGbc.weightx = 0;
        dividerGbc.fill = GridBagConstraints.NONE;
        dividerPanel.add(orLabel, dividerGbc);

        JSeparator separator2 = new JSeparator();
        dividerGbc.gridx = 2;
        dividerGbc.weightx = 1.0;
        dividerGbc.fill = GridBagConstraints.HORIZONTAL;
        dividerPanel.add(separator2, dividerGbc);

        signup = createStyledButton("Create Account", Color.WHITE, new Color(0, 123, 255));
        signup.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                new EmptyBorder(12, 0, 12, 0)
        ));
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners
        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(logIn)) {
                    final LoginState currentState = loginViewModel.getState();
                    loginController.execute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                }
            }
        });

        signup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginController.toSignup();
            }
        });

        // Add document listeners
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Add Enter key support
        passwordField.addActionListener(e -> logIn.doClick());

        // Add components to card panel
        cardPanel.add(logoPanel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(subtitle);
        cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(usernamePanel);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(passwordPanel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(errorLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(logIn);
        cardPanel.add(dividerPanel);
        cardPanel.add(signup);

        // Add card panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(cardPanel, gbc);
    }

    private JPanel createStyledInputPanel(String labelText, JTextField textField, String icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 70));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(80, 80, 80));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        textField.setBorder(null);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);

        // Add focus listeners for visual feedback
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        inputPanel.add(iconLabel, BorderLayout.WEST);
        inputPanel.add(textField, BorderLayout.CENTER);

        panel.add(label, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(fg);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(400, 45));
        button.setPreferredSize(new Dimension(400, 45));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bg.equals(Color.WHITE)) {
                    button.setBackground(new Color(248, 249, 250));
                } else {
                    button.setBackground(bg.darker());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        resetFields();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        if (state.getLoginError() != null && !state.getLoginError().isEmpty()) {
            errorLabel.setText("⚠ " + state.getLoginError());
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
        }
    }

    private void setFields(LoginState state) {
        usernameField.setText(state.getUsername());
        passwordField.setText(state.getPassword());
    }

    private void resetFields(){
        usernameField.setText("");
        passwordField.setText("");
        errorLabel.setVisible(false);
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}