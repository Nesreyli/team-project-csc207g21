package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.watchlist.WatchlistController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import view.theme.StyledButton;
import view.theme.StyledButton.*;
import view.theme.StyledCardPanel;
import view.theme.UiTheme;
import view.theme.UiTheme.*;

public class WatchlistPreviewPanel extends StyledCardPanel implements PropertyChangeListener {

    private final WatchlistViewModel watchlistViewModel;
    private WatchlistController watchlistController;

    private final JPanel rowPanel;

    public WatchlistPreviewPanel(WatchlistViewModel watchlistViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        watchlistViewModel.addPropertyChangeListener(this);

        final JLabel header = new JLabel("Your Watchlist");
        header.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 18));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(Color.WHITE);

        final JScrollPane scroll = new JScrollPane(rowPanel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(null);

        final JButton moreButton = StyledButton.createOutlineButton("View Full Watchlist");
        moreButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        moreButton.addActionListener(e -> {
            final WatchlistState state = this.watchlistViewModel.getState();
            if (watchlistController != null) {
                watchlistController.openWatchlist(state.getUsername(), state.getPassword());
            }
        });

        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(scroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(moreButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final WatchlistState state = (WatchlistState) evt.getNewValue();
        rowPanel.removeAll();

        if (state.getSymbols() == null || state.getSymbols().isEmpty()) {
            rowPanel.add(new JLabel("No items in your watchlist."));
        }
        else {
            for (String symbol : state.getSymbols()) {

                final JPanel box = new JPanel();
                box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
                box.setBackground(Color.WHITE);
                box.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));

                final JLabel symLabel = new JLabel(symbol);
                symLabel.setFont(new Font(UiTheme.FONT_NAME, Font.BOLD, 14));

                final JLabel priceLabel = new JLabel("");
                if (state.getPrices() != null && state.getPrices().containsKey(symbol)) {
                    priceLabel.setText("Price: $" + state.getPrices().get(symbol));
                }
                priceLabel.setFont(new Font(UiTheme.FONT_NAME, Font.PLAIN, 12));

                final JLabel perfLabel = new JLabel("");
                if (state.getPerformance() != null && state.getPerformance().containsKey(symbol)) {
                    final var performance = state.getPerformance().get(symbol);
                    perfLabel.setText((performance.doubleValue() >= 0 ? "▲ " : "▼ ")
                            + performance
                            + "%");
                    perfLabel.setForeground(performance.doubleValue() >= 0 ? new Color(0, 153, 0) : Color.RED);
                }

                box.add(symLabel);
                box.add(priceLabel);
                box.add(perfLabel);
                rowPanel.add(box);
                rowPanel.add(Box.createRigidArea(new Dimension(15, 0)));
            }
        }

        rowPanel.revalidate();
        rowPanel.repaint();
    }

    public void setWatchlistController(WatchlistController watchlistController) {
        this.watchlistController = watchlistController;
    }
}
