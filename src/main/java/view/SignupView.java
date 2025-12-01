package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();
    private SignupController signupController;
    private final JButton signUp;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Set layout and background
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(240, 242, 245));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create main card panel
        final JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));

        // Logo/Icon area
        final JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        final JLabel logoLabel = new JLabel("📈");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoPanel.add(logoLabel);

        // Title
        final JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(51, 51, 51));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel subtitle = new JLabel("Join Panic Trade today");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 120, 120));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input fields with custom styling
        final JPanel usernamePanel = createStyledInputPanel(SignupViewModel.USERNAME_LABEL,
                usernameInputField, "👤");
        final JPanel passwordPanel = createStyledInputPanel(SignupViewModel.PASSWORD_LABEL,
                passwordInputField, "🔒");
        final JPanel repeatPasswordPanel = createStyledInputPanel(SignupViewModel.REPEAT_PASSWORD_LABEL,
                repeatPasswordInputField, "🔒");

        // Password strength indicator
        final JLabel passwordHint = new JLabel("Password must be at least 5 characters");
        passwordHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        passwordHint.setForeground(new Color(120, 120, 120));
        passwordHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Error label
        errorLabel.setForeground(new Color(220, 53, 69));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        // Buttons panel
        signUp = createStyledButton(SignupViewModel.SIGNUP_BUTTON_LABEL,
                new Color(40, 167, 69), Color.WHITE);
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Divider with "Already have an account?"
        final JPanel dividerPanel = new JPanel();
        dividerPanel.setLayout(new GridBagLayout());
        dividerPanel.setBackground(Color.WHITE);
        dividerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        dividerPanel.setMaximumSize(new Dimension(400, 40));

        final GridBagConstraints dividerGbc = new GridBagConstraints();
        dividerGbc.fill = GridBagConstraints.HORIZONTAL;
        dividerGbc.insets = new Insets(0, 5, 0, 5);

        final JSeparator separator1 = new JSeparator();
        dividerGbc.gridx = 0;
        dividerGbc.weightx = 1.0;
        dividerPanel.add(separator1, dividerGbc);

        final JLabel alreadyLabel = new JLabel("Already have an account?");
        alreadyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        alreadyLabel.setForeground(new Color(120, 120, 120));
        dividerGbc.gridx = 1;
        dividerGbc.weightx = 0;
        dividerGbc.fill = GridBagConstraints.NONE;
        dividerPanel.add(alreadyLabel, dividerGbc);

        final JSeparator separator2 = new JSeparator();
        dividerGbc.gridx = 2;
        dividerGbc.weightx = 1.0;
        dividerGbc.fill = GridBagConstraints.HORIZONTAL;
        dividerPanel.add(separator2, dividerGbc);

        toLogin = createStyledButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL,
                Color.WHITE, new Color(0, 123, 255));
        toLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                new EmptyBorder(12, 0, 12, 0)
        ));
        toLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners
        signUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(signUp)) {
                    final SignupState currentState = signupViewModel.getState();
                    signupController.execute(
                            currentState.getUsername(),
                            currentState.getPassword(),
                            currentState.getRepeatPassword()
                    );
                }
            }
        });

        toLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signupController.switchToLoginView();
            }
        });

        // Add document listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        // Add Enter key support
        repeatPasswordInputField.addActionListener(event -> signUp.doClick());

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
        cardPanel.add(Box.createVerticalStrut(5));
        cardPanel.add(passwordHint);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(repeatPasswordPanel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(errorLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(signUp);
        cardPanel.add(dividerPanel);
        cardPanel.add(toLogin);

        // Add card panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(cardPanel, gbc);
    }

    private JPanel createStyledInputPanel(String labelText, JTextField textField, String icon) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 70));

        final JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(80, 80, 80));

        final JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        final JLabel iconLabel = new JLabel(icon);
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

    private JButton createStyledButton(String text, Color background, Color foreground) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(400, 45));
        button.setPreferredSize(new Dimension(400, 45));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (background.equals(Color.WHITE)) {
                    button.setBackground(new Color(248, 249, 250));
                }
                else {
                    button.setBackground(background.darker());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(background);
            }
        });

        return button;
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        resetField();
    }

    private void setFields(SignupState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
        repeatPasswordInputField.setText(state.getRepeatPassword());
    }

    /**
     * Resets the input fields and error label in the sign-up form.
     * This method clears the text of the username, password, and repeat password
     * fields, and hides the error label. It is typically called after a successful
     * sign-up or when resetting the form.
     */
    public void resetField() {
        usernameInputField.setText("");
        passwordInputField.setText("");
        repeatPasswordInputField.setText("");
        errorLabel.setVisible(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        setFields(state);

        if (state.error() != null && !state.error().isEmpty()) {
            errorLabel.setText("⚠ " + state.error());
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
