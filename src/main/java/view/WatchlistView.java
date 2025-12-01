package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.*;

import interface_adapter.homebutton.HomeController;
import interface_adapter.remove_watchlist.RemoveFromWatchlistController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

/**
 * The Watchlist View displaying stocks that were chosen to be monitored by the user.
 */
public class WatchlistView extends JPanel implements PropertyChangeListener {

    private static final int SPACING = 20;
    private static final int ROW_HEIGHT = 45;

    private final String viewName = "watchlist";
    private final WatchlistViewModel watchlistViewModel;
    private RemoveFromWatchlistController removeController;
    private HomeController homeController;

    private final JButton backButton;
    private final JPanel symbolsPanel;
    private final JLabel notificationLabel;

    private final String fontName = "Segoe UI";

    public WatchlistView(WatchlistViewModel watchlistViewModel,
                         RemoveFromWatchlistController removeController) {

        this.watchlistViewModel = watchlistViewModel;
        this.removeController = removeController;
        this.watchlistViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(240, 242, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setMaximumSize(new Dimension(120, 35));
        backButton.setFont(new Font(fontName, Font.BOLD, 14));
        backButton.setBackground(new Color(230, 230, 230));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this::backClicked);

        final JLabel title = new JLabel("Watchlist");
        title.setFont(new Font(fontName, Font.BOLD, 26));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        notificationLabel = new JLabel("");
        notificationLabel.setFont(new Font(fontName, Font.PLAIN, 14));
        notificationLabel.setForeground(new Color(50, 150, 50));
        notificationLabel.setVisible(false);
        notificationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(backButton);
        headerPanel.add(Box.createVerticalStrut(SPACING / 2));
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(SPACING / 2));
        headerPanel.add(notificationLabel);
        headerPanel.add(Box.createVerticalStrut(SPACING));

        add(headerPanel, BorderLayout.NORTH);

        symbolsPanel = new JPanel();
        symbolsPanel.setLayout(new BoxLayout(symbolsPanel, BoxLayout.Y_AXIS));
        symbolsPanel.setBackground(Color.WHITE);
        symbolsPanel.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));

        final JScrollPane scrollPane = new JScrollPane(symbolsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        final JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(scrollPane, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private void backClicked(ActionEvent evt) {
        final WatchlistState state = watchlistViewModel.getState();
        homeController.execute(state.getUsername(), state.getPassword());
        notificationLabel.setVisible(false);
        notificationLabel.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!evt.getPropertyName().equals("state")) {
            return;
        }
        final WatchlistState state = (WatchlistState) evt.getNewValue();
        updateSymbols(state);
    }

    private void updateSymbols(WatchlistState state) {
        symbolsPanel.removeAll();

        final List<String> symbols = state.getSymbols();

        if (symbols == null || symbols.isEmpty()) {
            final JLabel empty = new JLabel("Your watchlist is empty.");
            empty.setFont(new Font(fontName, Font.ITALIC, 16));
            symbolsPanel.add(empty);
            symbolsPanel.revalidate();
            symbolsPanel.repaint();
            return;
        }

        for (String symbol : symbols) {
            final JPanel row = new JPanel(new GridBagLayout());
            row.setBackground(Color.WHITE);
            row.setPreferredSize(new Dimension(600, ROW_HEIGHT));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));

            final GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 5, 0, 5);
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.weightx = 0.3;
            final JLabel symbolLabel = new JLabel(symbol);
            symbolLabel.setFont(new Font(fontName, Font.BOLD, 15));
            row.add(symbolLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.2;
            final JLabel price = new JLabel("");
            if (state.getPrices() != null && state.getPrices().containsKey(symbol)) {
                price.setText("Price: $" + state.getPrices().get(symbol));
            }
            price.setFont(new Font(fontName, Font.PLAIN, 14));
            row.add(price, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.2;
            final JLabel perf = new JLabel("");
            if (state.getPerformance() != null && state.getPerformance().containsKey(symbol)) {
                final BigDecimal p = state.getPerformance().get(symbol);
                perf.setText(p + "%");
                if (p.compareTo(BigDecimal.ZERO) > 0) {
                    perf.setForeground(new Color(0, 165, 0));
                }
                else if (p.compareTo(BigDecimal.ZERO) < 0) {
                    perf.setForeground(new Color(200, 0, 0));
                }
            }
            perf.setFont(new Font(fontName, Font.BOLD, 14));
            row.add(perf, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            final JButton remove = new JButton("Remove");
            remove.setPreferredSize(new Dimension(100, 32));
            remove.setFocusPainted(false);
            remove.setCursor(new Cursor(Cursor.HAND_CURSOR));
            remove.addActionListener(e -> {
                removeController.remove(state.getUsername(), state.getPassword(), symbol);
                notificationLabel.setText(symbol + " removed from watchlist");
                notificationLabel.setVisible(true);
            });
            row.add(remove, gbc);

            symbolsPanel.add(row);
            symbolsPanel.add(Box.createVerticalStrut(SPACING));
        }

        symbolsPanel.revalidate();
        symbolsPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
