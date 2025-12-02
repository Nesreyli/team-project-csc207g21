package view;

import interface_adapter.homebutton.HomeController;
import interface_adapter.watchlist.WatchlistController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;

import static view.theme.StyledButton.createOutlineButton;
import static view.theme.UITheme.*;

/**
 * WatchlistView displays the user's watchlist in a card-style layout.
 * Listens to changes in WatchlistViewModel to update the list of symbols,
 * prices, and performance.
 */
public class WatchlistView extends JPanel implements PropertyChangeListener {

    private final String viewName = "watchlist";

    private final WatchlistViewModel watchlistViewModel;
    private WatchlistController watchlistController;
    private HomeController homeController;

    private JLabel notificationLabel;
    private JPanel symbolsPanel;
    private JButton backButton;

    public WatchlistView(WatchlistViewModel watchlistViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.watchlistViewModel.addPropertyChangeListener(this);

        setBackground(BG);
        setLayout(new GridBagLayout());

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("📋 Your Watchlist");
        title.setFont(new Font(FONT_NAME, Font.BOLD, 26));
        title.setForeground(TEXT_DARK);

        notificationLabel = new JLabel("");
        notificationLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        notificationLabel.setForeground(new Color(50, 150, 50));
        notificationLabel.setVisible(false);

        backButton = createOutlineButton("Back");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(this::backClicked);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(CARD_BG);
        header.add(backButton);
        header.add(Box.createVerticalStrut(15));
        header.add(title);
        header.add(Box.createVerticalStrut(10));
        header.add(notificationLabel);

        card.add(header);
        card.add(Box.createVerticalStrut(20));

        symbolsPanel = new JPanel();
        symbolsPanel.setLayout(new BoxLayout(symbolsPanel, BoxLayout.Y_AXIS));
        symbolsPanel.setBackground(CARD_BG);

        JScrollPane scrollPane = new JScrollPane(symbolsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        card.add(scrollPane);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    private void backClicked(ActionEvent evt) {
        WatchlistState state = watchlistViewModel.getState();
        homeController.execute(state.getUsername(), state.getPassword());
        notificationLabel.setVisible(false);
        notificationLabel.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!evt.getPropertyName().equals("state")) return;
        WatchlistState state = (WatchlistState) evt.getNewValue();
        updateSymbols(state);
    }

    private void updateSymbols(WatchlistState state) {
        symbolsPanel.removeAll();

        List<String> symbols = state.getSymbols();
        if (symbols == null || symbols.isEmpty()) {
            JLabel empty = new JLabel("Your watchlist is empty.");
            empty.setFont(new Font(FONT_NAME, Font.ITALIC, 16));
            symbolsPanel.add(empty);
        } else {
            for (String symbol : symbols) {
                JPanel row = new JPanel(new GridBagLayout());
                row.setBackground(CARD_BG);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 5, 0, 5);
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.weightx = 0.3;
                JLabel symbolLabel = new JLabel(symbol);
                symbolLabel.setFont(new Font(FONT_NAME, Font.BOLD, 15));
                row.add(symbolLabel, gbc);


                gbc.gridx = 1;
                gbc.weightx = 0.2;
                JLabel priceLabel = new JLabel("");
                if (state.getPrices() != null && state.getPrices().containsKey(symbol)) {
                    priceLabel.setText("Price: $" + state.getPrices().get(symbol));
                }
                priceLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
                row.add(priceLabel, gbc);

                gbc.gridx = 2;
                gbc.weightx = 0.2;
                JLabel perfLabel = new JLabel("");
                if (state.getPerformance() != null && state.getPerformance().containsKey(symbol)) {
                    BigDecimal p = state.getPerformance().get(symbol);
                    perfLabel.setText(p + "%");
                    if (p.compareTo(BigDecimal.ZERO) > 0) perfLabel.setForeground(new Color(0, 165, 0));
                    else if (p.compareTo(BigDecimal.ZERO) < 0) perfLabel.setForeground(new Color(200, 0, 0));
                }
                perfLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
                row.add(perfLabel, gbc);

                gbc.gridx = 3;
                gbc.weightx = 0.1;
                JButton removeButton = createOutlineButton("Remove");
                removeButton.addActionListener(e -> {
                    watchlistController.remove(state.getUsername(), state.getPassword(), symbol);
                    notificationLabel.setText(symbol + " removed from watchlist");
                    notificationLabel.setVisible(true);
                });
                row.add(removeButton, gbc);

                symbolsPanel.add(row);
                symbolsPanel.add(Box.createVerticalStrut(10));
            }
        }

        symbolsPanel.revalidate();
        symbolsPanel.repaint();
    }

    public String getViewName() { return viewName; }
    public void setHomeController(HomeController c) { homeController = c; }
    public void setWatchlistController(WatchlistController c) { watchlistController = c; }
}
