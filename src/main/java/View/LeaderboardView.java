package View;

import InterfaceAdapter.homebutton.HomeController;
import InterfaceAdapter.leaderboard.LeaderboardState;
import InterfaceAdapter.leaderboard.LeaderboardViewModel;
import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class LeaderboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "leaderboard";
    private final LeaderboardViewModel leaderboardViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final JButton homeButton;
    private final JPanel leaderboardPanel;
    private final JLabel titleLabel;
    private HomeController homeController;
    
    // Color constants
    private static final Color BRIGHT = new Color(214, 216, 220);
    private static final Color DARK = new Color(43, 45, 48);
    private static final Color ACCENT_GOLD = new Color(255, 215, 0);
    private static final Color ACCENT_SILVER = new Color(192, 192, 192);
    private static final Color ACCENT_BRONZE = new Color(205, 127, 50);
    private static final Color HEADER_BG = new Color(60, 63, 66);
    private static final Color CARD_BG = new Color(55, 58, 61);
    private static final Color TOP_THREE_BG = new Color(70, 73, 76);

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel, LoggedInViewModel loggedInViewModel) {
        this.leaderboardViewModel = leaderboardViewModel;
        this.leaderboardViewModel.addPropertyChangeListener(this);
        this.loggedInViewModel = loggedInViewModel;

        this.setLayout(new BorderLayout());
        this.setBackground(DARK);

        // Title
        titleLabel = new JLabel("Leaderboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(ACCENT_GOLD);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, ACCENT_GOLD),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        // Back arrow button in top-left corner
        homeButton = new JButton("←");
        homeButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        homeButton.setForeground(BRIGHT);
        homeButton.setBackground(DARK);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setContentAreaFilled(false);
        homeButton.addActionListener(this);

        // Leaderboard panel (no scrollbar - everything fits)
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setBackground(DARK);
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(3, 20, 3, 20));

        // Top panel with title centered and arrow overlaid on top
        JPanel topPanel = new JPanel() {
            @Override
            public void doLayout() {
                super.doLayout();
                // Position title to span full width and center vertically
                if (titleLabel.getParent() == this) {
                    titleLabel.setBounds(0, 0, getWidth(), getHeight());
                }
                // Position button in top-left, overlaid on title
                if (homeButton.getParent() == this) {
                    homeButton.setBounds(10, 10, 40, 40);
                }
            }
        };
        topPanel.setLayout(null);
        topPanel.setBackground(DARK);
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.add(titleLabel);
        topPanel.add(homeButton);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(leaderboardPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(homeButton)) {
            final LoggedInState currentState = loggedInViewModel.getState();
            if (homeController != null) {
                homeController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LeaderboardState state = (LeaderboardState) evt.getNewValue();
            leaderboardPanel.removeAll();

            if (state.getError() != null) {
                JPanel errorPanel = new JPanel();
                errorPanel.setBackground(DARK);
                errorPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 2),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
                JLabel errorLabel = new JLabel("Error: " + state.getError());
                errorLabel.setForeground(Color.RED);
                errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                errorPanel.add(errorLabel);
                leaderboardPanel.add(errorPanel);
            } else if (!state.hasEnoughUsers()) {
                // Display "Waiting for competition" if fewer than 2 users
                JPanel waitingPanel = new JPanel();
                waitingPanel.setBackground(DARK);
                waitingPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 80, 20));
                JLabel waitingLabel = new JLabel("Waiting for competition");
                waitingLabel.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 24));
                waitingLabel.setForeground(new Color(BRIGHT.getRed(), BRIGHT.getGreen(), BRIGHT.getBlue(), 150));
                waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
                waitingPanel.add(waitingLabel);
                leaderboardPanel.add(waitingPanel);
            } else {
                // Display leaderboard
                Map<Integer, List<Object>> leaderboard = state.getLeaderboard();
                if (leaderboard != null && !leaderboard.isEmpty()) {
                    // Styled Header with custom column widths
                    JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
                    headerPanel.setBackground(HEADER_BG);
                    headerPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 0, BRIGHT.darker()),
                            BorderFactory.createEmptyBorder(6, 15, 6, 15)
                    ));

                    JLabel rankHeader = new JLabel("Rank");
                    rankHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    rankHeader.setForeground(BRIGHT);
                    rankHeader.setHorizontalAlignment(SwingConstants.CENTER);
                    rankHeader.setPreferredSize(new Dimension(60, 0)); // Narrower for rank

                    JLabel userHeader = new JLabel("Username");
                    userHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    userHeader.setForeground(BRIGHT);
                    userHeader.setHorizontalAlignment(SwingConstants.LEFT);

                    JLabel valueHeader = new JLabel("Portfolio Value");
                    valueHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    valueHeader.setForeground(BRIGHT);
                    valueHeader.setHorizontalAlignment(SwingConstants.CENTER);

                    headerPanel.add(rankHeader, BorderLayout.WEST);
                    headerPanel.add(userHeader, BorderLayout.CENTER);
                    headerPanel.add(valueHeader, BorderLayout.EAST);
                    leaderboardPanel.add(headerPanel);
                    leaderboardPanel.add(Box.createVerticalStrut(5));

                    // Display entries sorted by rank (limit to top 10 to fit in window)
                    leaderboard.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .limit(10) // Limit to top 10 entries
                            .forEach(entry -> {
                                Integer rank = entry.getKey();
                                List<Object> userData = entry.getValue();

                                if (userData != null && userData.size() >= 2) {
                                    Object usernameObj = userData.get(0);
                                    Object valueObj = userData.get(1);
                                    
                                    // Handle null or empty username - skip this entry
                                    if (usernameObj == null || usernameObj.toString().trim().isEmpty()) {
                                        return; // Skip entries with empty usernames
                                    }
                                    
                                    String username = usernameObj.toString();
                                    String valueStr;
                                    
                                    if (valueObj == null) {
                                        valueStr = "N/A";
                                    } else if (valueObj instanceof BigDecimal) {
                                        BigDecimal bd = (BigDecimal) valueObj;
                                        valueStr = String.format("$%,.2f", bd);
                                    } else if (valueObj instanceof Number) {
                                        valueStr = String.format("$%,.2f", ((Number) valueObj).doubleValue());
                                    } else {
                                        valueStr = valueObj.toString();
                                    }

                                    // Determine styling based on rank
                                    Color rankColor;
                                    Color bgColor;
                                    Font rankFont;
                                    String rankPrefix = "";
                                    
                                    if (rank == 1) {
                                        rankColor = ACCENT_GOLD;
                                        bgColor = TOP_THREE_BG;
                                        rankFont = new Font("Segoe UI", Font.BOLD, 14);
                                        rankPrefix = "";
                                    } else if (rank == 2) {
                                        rankColor = ACCENT_SILVER;
                                        bgColor = TOP_THREE_BG;
                                        rankFont = new Font("Segoe UI", Font.BOLD, 14);
                                        rankPrefix = "";
                                    } else if (rank == 3) {
                                        rankColor = ACCENT_BRONZE;
                                        bgColor = TOP_THREE_BG;
                                        rankFont = new Font("Segoe UI", Font.BOLD, 14);
                                        rankPrefix = "";
                                    } else {
                                        rankColor = BRIGHT;
                                        bgColor = CARD_BG;
                                        rankFont = new Font("Segoe UI", Font.BOLD, 13);
                                        rankPrefix = "";
                                    }

                                    // Create styled entry panel (clickable) with custom column widths
                                    JPanel entryPanel = new JPanel(new BorderLayout(15, 0));
                                    entryPanel.setBackground(bgColor);
                                    entryPanel.setBorder(BorderFactory.createCompoundBorder(
                                            BorderFactory.createRaisedBevelBorder(),
                                            BorderFactory.createEmptyBorder(5, 15, 5, 15)
                                    ));
                                    entryPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    // Make panel clickable to show purchases
                                    final String finalUsername = username;
                                    final Color finalBgColor = bgColor;
                                    
                                    // Simple hover handler - only panel handles exit
                                    entryPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                                        @Override
                                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                            UserPurchasesView purchasesView = new UserPurchasesView(finalUsername);
                                            purchasesView.setVisible(true);
                                        }
                                        
                                        @Override
                                        public void mouseEntered(java.awt.event.MouseEvent e) {
                                            entryPanel.setBackground(finalBgColor.brighter());
                                        }
                                        
                                        @Override
                                        public void mouseExited(java.awt.event.MouseEvent e) {
                                            entryPanel.setBackground(finalBgColor);
                                        }
                                    });
                                    
                                    // Labels handle click and maintain hover (no exit handling)
                                    java.awt.event.MouseAdapter labelAdapter = new java.awt.event.MouseAdapter() {
                                        @Override
                                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                            UserPurchasesView purchasesView = new UserPurchasesView(finalUsername);
                                            purchasesView.setVisible(true);
                                        }
                                        
                                        @Override
                                        public void mouseEntered(java.awt.event.MouseEvent e) {
                                            entryPanel.setBackground(finalBgColor.brighter());
                                        }
                                    };

                                    // Rank label with special styling for top 3 - narrower column
                                    JPanel rankPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                    rankPanel.setOpaque(false);
                                    rankPanel.setPreferredSize(new Dimension(60, 0)); // Narrower for rank
                                    JLabel rankLabel = new JLabel(rankPrefix + rank.toString());
                                    rankLabel.setFont(rankFont);
                                    rankLabel.setForeground(rankColor);
                                    rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                    rankLabel.setOpaque(false);
                                    rankLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    rankLabel.addMouseListener(labelAdapter);
                                    rankPanel.add(rankLabel);

                                    // Username label - smaller than header, left-aligned
                                    JLabel userLabel = new JLabel(username);
                                    if (rank <= 3) {
                                        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                                        userLabel.setForeground(rankColor);
                                    } else {
                                        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                                        userLabel.setForeground(BRIGHT);
                                    }
                                    userLabel.setHorizontalAlignment(SwingConstants.LEFT);
                                    userLabel.setOpaque(false);
                                    userLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    userLabel.addMouseListener(labelAdapter);

                                    // Value label with currency formatting - smaller than header
                                    JLabel valueLabel = new JLabel(valueStr);
                                    if (rank <= 3) {
                                        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                                        valueLabel.setForeground(rankColor);
                                    } else {
                                        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
                                        valueLabel.setForeground(BRIGHT); // Use same color as other text
                                    }
                                    valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                    valueLabel.setOpaque(false);
                                    valueLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    valueLabel.addMouseListener(labelAdapter);

                                    entryPanel.add(rankPanel, BorderLayout.WEST);
                                    entryPanel.add(userLabel, BorderLayout.CENTER);
                                    entryPanel.add(valueLabel, BorderLayout.EAST);
                                    leaderboardPanel.add(entryPanel);
                                    leaderboardPanel.add(Box.createVerticalStrut(2));
                                }
                            });
                }
            }

            leaderboardPanel.revalidate();
            leaderboardPanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}

