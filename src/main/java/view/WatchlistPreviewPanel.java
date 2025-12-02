package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import interface_adapter.watchlist.WatchlistController;
import view.theme.StyledCardPanel;

import static view.theme.StyledButton.createOutlineButton;
import static view.theme.UITheme.FONT_NAME;

public class WatchlistPreviewPanel extends StyledCardPanel implements PropertyChangeListener {

    private final WatchlistViewModel watchlistViewModel;
    private WatchlistController watchlistController;

    private final JPanel rowPanel;

    public WatchlistPreviewPanel(WatchlistViewModel watchlistViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        watchlistViewModel.addPropertyChangeListener(this);

        JLabel header = new JLabel("Your Watchlist");
        header.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(rowPanel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(null);

        JButton moreButton = createOutlineButton("View Full Watchlist");
        moreButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        moreButton.addActionListener(e -> {
            WatchlistState s = this.watchlistViewModel.getState();
            if (watchlistController != null) {
                watchlistController.openWatchlist(s.getUsername(), s.getPassword());
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
        WatchlistState s = (WatchlistState) evt.getNewValue();
        rowPanel.removeAll();

        if (s.getSymbols() == null || s.getSymbols().isEmpty()) {
            rowPanel.add(new JLabel("No items in your watchlist."));
        } else {
            for (String symbol : s.getSymbols()) {

                JPanel box = new JPanel();
                box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
                box.setBackground(Color.WHITE);
                box.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220,220,220),1),
                        BorderFactory.createEmptyBorder(8,12,8,12)
                ));

                JLabel symLabel = new JLabel(symbol);
                symLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));

                JLabel priceLabel = new JLabel("");
                if (s.getPrices() != null && s.getPrices().containsKey(symbol)) {
                    priceLabel.setText("Price: $" + s.getPrices().get(symbol));
                }
                priceLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

                JLabel perfLabel = new JLabel("");
                if (s.getPerformance() != null && s.getPerformance().containsKey(symbol)) {
                    var p = s.getPerformance().get(symbol);
                    perfLabel.setText((p.doubleValue() >= 0 ? "▲ " : "▼ ") + p + "%");
                    perfLabel.setForeground(p.doubleValue() >= 0 ? new Color(0, 153, 0) : Color.RED);
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

    public void setWatchlistController(WatchlistController c) {
        this.watchlistController = c;
    }
}
