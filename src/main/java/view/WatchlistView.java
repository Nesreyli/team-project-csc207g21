package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;

import interface_adapter.homebutton.HomeController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

public class WatchlistView extends JPanel implements PropertyChangeListener {
    private final String viewName = "watchlist";
    private final WatchlistViewModel watchlistViewModel;

    private final JButton username;
    private final JPanel symbolsPanel;

    private HomeController homeController;

    public WatchlistView(WatchlistViewModel watchlistViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.watchlistViewModel.addPropertyChangeListener(this);

        username = new JButton();
        username.addActionListener(this::usernameClicked);

        final JLabel userLabel = new JLabel("Watchlist:");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        symbolsPanel = new JPanel();
        symbolsPanel.setLayout(new BoxLayout(symbolsPanel, BoxLayout.Y_AXIS));
        symbolsPanel.setBackground(Color.LIGHT_GRAY);

        final JScrollPane scroll = new JScrollPane(symbolsPanel);
        scroll.setBackground(Color.LIGHT_GRAY);

        final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(username);
        topPanel.add(userLabel);
        topPanel.setBackground(new Color(211, 211, 211));

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.setBackground(Color.LIGHT_GRAY);
    }

    private void usernameClicked(ActionEvent evt) {
        final WatchlistState state = watchlistViewModel.getState();
        homeController.execute(state.getUsername(), state.getPassword());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final WatchlistState state = (WatchlistState) evt.getNewValue();
            username.setText(state.getUsername());

            symbolsPanel.removeAll();
            final List<String> symbols = state.getSymbols();
            if (symbols == null || symbols.isEmpty()) {
                final JLabel emptyLabel = new JLabel("Your watchlist is empty.");
                emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
                symbolsPanel.add(emptyLabel);
            }
            else {
                symbols.forEach(symbol -> {
                    final JPanel symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    symbolPanel.setBackground(Color.LIGHT_GRAY);

                    final JLabel symbolLabel = new JLabel(symbol);
                    symbolPanel.add(symbolLabel);

                    if (state.getPrices() != null && state.getPrices().containsKey(symbol)) {
                        final JLabel priceLabel = new JLabel("Price: $" + state.getPrices().get(symbol));
                        symbolPanel.add(priceLabel);
                    }

                    if (state.getPerformance() != null && state.getPerformance().containsKey(symbol)) {
                        final JLabel perfLabel = new JLabel("Perf: " + state.getPerformance().get(symbol) + "%");
                        symbolPanel.add(perfLabel);
                    }

                    final JButton removeButton = new JButton("Remove");
                    removeButton.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, "Removed " + symbol + " from watchlist");
                    });
                    symbolPanel.add(removeButton);

                    symbolsPanel.add(symbolPanel);
                });
            }

            symbolsPanel.revalidate();
            symbolsPanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
