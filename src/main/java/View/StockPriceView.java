package View;

import InterfaceAdapter.addToWatchlist.AddToWatchlistController;
import InterfaceAdapter.addToWatchlist.AddToWatchlistViewModel;
import InterfaceAdapter.logged_in.LoggedInViewModel;
import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PriceState;
import InterfaceAdapter.stock_price.PriceViewModel;
import InterfaceAdapter.removeFromWatchlist.RemoveFromWatchlistController;
import InterfaceAdapter.removeFromWatchlist.RemoveFromWatchlistViewModel;
import InterfaceAdapter.watchlist.WatchlistController;
import InterfaceAdapter.watchlist.WatchlistViewModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class StockPriceView extends JFrame implements PropertyChangeListener {
    private final String viewName = "Stock Price";
    private PriceViewModel priceViewModel;
    private PriceController priceController;
    private WatchlistController watchlistController;
    private AddToWatchlistViewModel addToWatchlistViewModel;
    private AddToWatchlistController addToWatchlistController;
    private RemoveFromWatchlistViewModel removeFromWatchlistViewModel;
    private RemoveFromWatchlistController removeFromWatchlistController;
    private WatchlistViewModel watchlistViewModel;
    private LoggedInViewModel loggedInViewModel;

    private final JLabel symbol;
    private final JLabel company;
    private final JLabel country;
    private final JLabel price;
    private final JLabel ytdPrice;
    private final JLabel ytdPerformance;
    private final JButton buy;
    private final JButton sell;
    private final JButton watchlistButton;
    private JLabel companyValue;
    private JLabel symbolValue;
    private String username;
    private String password;

    public StockPriceView(
            PriceViewModel priceViewModel,
            AddToWatchlistViewModel addVM,
            AddToWatchlistController addCtrl,
            RemoveFromWatchlistViewModel removeVM,
            RemoveFromWatchlistController removeCtrl,
            WatchlistViewModel watchlistVM,
            LoggedInViewModel loggedInVM,
            WatchlistController watchlistController
    ) {
        this.priceViewModel = priceViewModel;
        this.priceViewModel.addPropertyChangeListener(this);
        this.addToWatchlistViewModel = addVM;
        this.addToWatchlistController = addCtrl;
        this.removeFromWatchlistViewModel = removeVM;
        this.removeFromWatchlistController = removeCtrl;
        this.watchlistViewModel = watchlistVM;
        this.loggedInViewModel = loggedInVM;
        this.watchlistController = watchlistController;

        symbol = new JLabel();
        company = new JLabel();
        country = new JLabel();
        price = new JLabel();
        ytdPrice = new JLabel();
        ytdPerformance = new JLabel();
        buy = new JButton("Buy Stock");
        sell = new JButton("Sell Stock");
        watchlistButton = new JButton();

        this.setTitle("Stock Details");
        this.setSize(700, 450);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setBackground(new Color(240, 248, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel leftHeader = new JPanel();
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.setBackground(new Color(240, 248, 255));
        symbol.setFont(new Font("Arial", Font.BOLD, 32));
        symbol.setAlignmentX(Component.LEFT_ALIGNMENT);
        company.setFont(new Font("Arial", Font.BOLD, 18));
        company.setForeground(Color.DARK_GRAY);
        company.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftHeader.add(symbol);
        leftHeader.add(Box.createVerticalStrut(5));
        leftHeader.add(company);

        JPanel rightHeader = new JPanel();
        rightHeader.setLayout(new BoxLayout(rightHeader, BoxLayout.Y_AXIS));
        rightHeader.setBackground(new Color(240, 248, 255));
        price.setFont(new Font("Arial", Font.BOLD, 28));
        price.setAlignmentX(Component.RIGHT_ALIGNMENT);
        ytdPerformance.setFont(new Font("Arial", Font.BOLD, 16));
        ytdPerformance.setAlignmentX(Component.RIGHT_ALIGNMENT);
        ytdPrice.setFont(new Font("Arial", Font.BOLD, 12));
        ytdPrice.setForeground(Color.GRAY);
        ytdPrice.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightHeader.add(price);
        rightHeader.add(Box.createVerticalStrut(5));
        rightHeader.add(ytdPerformance);
        rightHeader.add(Box.createVerticalStrut(3));
        rightHeader.add(ytdPrice);

        headerPanel.add(leftHeader, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 20, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel countryLabelTitle = new JLabel("Country:");
        countryLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        countryLabelTitle.setForeground(Color.DARK_GRAY);
        JLabel companyLabelTitle = new JLabel("Company:");
        companyLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        companyLabelTitle.setForeground(Color.DARK_GRAY);
        JLabel symbolLabelTitle = new JLabel("Symbol:");
        symbolLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        symbolLabelTitle.setForeground(Color.DARK_GRAY);

        country.setFont(new Font("Arial", Font.BOLD, 14));
        companyValue = new JLabel();
        companyValue.setFont(new Font("Arial", Font.BOLD, 14));
        symbolValue = new JLabel();
        symbolValue.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(countryLabelTitle);
        infoPanel.add(country);
        infoPanel.add(companyLabelTitle);
        infoPanel.add(companyValue);
        infoPanel.add(symbolLabelTitle);
        infoPanel.add(symbolValue);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        buy.setBorder(new MatteBorder(2, 2, 2, 2, new Color(0, 96, 5)));
        buy.setFocusPainted(false);
        buy.setFont(new Font("Arial", Font.BOLD, 14));
        buy.setPreferredSize(new Dimension(120, 40));
        buy.addActionListener(e -> showBuyDialog());

        sell.setBorder(new MatteBorder(2, 2, 2, 2, new Color(96, 0, 0)));
        sell.setFocusPainted(false);
        sell.setFont(new Font("Arial", Font.BOLD, 14));
        sell.setPreferredSize(new Dimension(120, 40));
        sell.addActionListener(e -> showSellDialog());

        watchlistButton.setFocusPainted(false);
        watchlistButton.setFont(new Font("Arial", Font.BOLD, 14));
        watchlistButton.setPreferredSize(new Dimension(160, 40));
        watchlistButton.addActionListener(e -> toggleWatchlist());

        JButton closeButton = new JButton("Close");
        closeButton.setBorder(new MatteBorder(2, 2, 2, 2, new Color(122, 122, 122, 55)));
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> this.dispose());

        buttonPanel.add(buy);
        buttonPanel.add(sell);
        buttonPanel.add(watchlistButton);
        buttonPanel.add(closeButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private void toggleWatchlist() {
        String symbol = priceViewModel.getState().getSymbol();
        String username = priceViewModel.getState().getUsername();
        String password = priceViewModel.getState().getPassword();

        if (isInWatchlist(symbol)) {
            removeFromWatchlistController.remove(username, password, symbol);
            JOptionPane.showMessageDialog(this, "Removed " + symbol + " from watchlist");
        } else {
            addToWatchlistController.add(username, password, symbol);
            JOptionPane.showMessageDialog(this, "Added " + symbol + " to watchlist");
        }

        updateWatchlistButton(symbol);
    }


    private boolean isInWatchlist(String symbol) {
        return watchlistViewModel.getState().getSymbols() != null &&
                watchlistViewModel.getState().getSymbols().contains(symbol);
    }

    private void updateWatchlistButton(String symbol) {
        if (isInWatchlist(symbol)) {
            watchlistButton.setText("Remove from Watchlist");
            watchlistButton.setBackground(new Color(244, 67, 54));
        } else {
            watchlistButton.setText("Add to Watchlist");
            watchlistButton.setBackground(new Color(76, 175, 80));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            this.setVisible(true);
            PriceState priceState = (PriceState) evt.getNewValue();

            symbol.setText(priceState.getSymbol());
            company.setText(priceState.getCompany());
            country.setText(priceState.getCountry());
            companyValue.setText(priceState.getCompany());
            symbolValue.setText(priceState.getSymbol());
            price.setText(priceState.getPrice().toString());
            ytdPrice.setText("YTD Open: " + priceState.getYtdPrice());

            String perfText = priceState.getYtdPerformance();
            ytdPerformance.setText(perfText);
            try {
                double perfValue = Double.parseDouble(perfText.replace("%", "").trim());
                ytdPerformance.setForeground(perfValue >= 0 ? new Color(76, 175, 80) : new Color(244, 67, 54));
            } catch (Exception e) {
                ytdPerformance.setForeground(Color.BLACK);
            }

            updateWatchlistButton(priceState.getSymbol());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setPriceController(PriceController priceController) {
        this.priceController = priceController;
    }

    private void showBuyDialog() {
        PriceState state = priceViewModel.getState();
        JDialog dialog = new JDialog(this, "Buy " + state.getSymbol(), true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel priceLabel = new JLabel("Delayed Price: " + state.getPrice());
        JTextField amountField = new JTextField(10);
        JLabel totalCost = new JLabel();
        amountField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateCost() {
                if (Pattern.matches("[0-9]+", amountField.getText())) {
                    totalCost.setText(state.getPrice().multiply(
                            new BigDecimal(Integer.parseInt(amountField.getText()))).toString());
                }
            }
            public void insertUpdate(DocumentEvent e) { updateCost(); }
            public void removeUpdate(DocumentEvent e) { updateCost(); }
            public void changedUpdate(DocumentEvent e) { updateCost(); }
        });

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(246, 246, 246));
        inputPanel.setBorder(new MatteBorder(2, 2, 0, 2, new Color(93, 110, 93)));
        inputPanel.add(new JLabel("Amount (shares):"));
        inputPanel.add(amountField);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(246, 246, 246));
        totalPanel.setBorder(new MatteBorder(0, 2, 2, 2, new Color(93, 110, 93)));
        totalPanel.add(new JLabel("Estimated Cost:"));
        totalPanel.add(totalCost);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton confirmButton = new JButton("Confirm Buy");
        confirmButton.setBackground(new Color(76, 175, 80));
        confirmButton.addActionListener(e -> {
            String amount = amountField.getText().trim();
            if (!amount.isEmpty()) {
                dialog.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(priceLabel);
        infoPanel.add(inputPanel);
        infoPanel.add(totalPanel);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showSellDialog() {
        PriceState state = priceViewModel.getState();
        JDialog dialog = new JDialog(this, "Sell " + state.getSymbol(), true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel priceLabel = new JLabel("Delayed Price: " + state.getPrice());
        JTextField amountField = new JTextField(10);
        JLabel totalCost = new JLabel();
        amountField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateCost() {
                if (Pattern.matches("[0-9]+", amountField.getText())) {
                    totalCost.setText(state.getPrice().multiply(
                            new BigDecimal(Integer.parseInt(amountField.getText()))).toString());
                }
            }
            public void insertUpdate(DocumentEvent e) { updateCost(); }
            public void removeUpdate(DocumentEvent e) { updateCost(); }
            public void changedUpdate(DocumentEvent e) { updateCost(); }
        });

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(246, 246, 246));
        inputPanel.setBorder(new MatteBorder(2, 2, 0, 2, new Color(62, 0, 0)));
        inputPanel.add(new JLabel("Amount (shares):"));
        inputPanel.add(amountField);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(246, 246, 246));
        totalPanel.setBorder(new MatteBorder(0, 2, 2, 2, new Color(62, 0, 0)));
        totalPanel.add(new JLabel("Estimated Cost:"));
        totalPanel.add(totalCost);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton confirmButton = new JButton("Confirm Sell");
        confirmButton.setBackground(new Color(244, 67, 54));
        confirmButton.addActionListener(e -> {
            String amount = amountField.getText().trim();
            if (!amount.isEmpty()) {
                dialog.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(priceLabel);
        infoPanel.add(inputPanel);
        infoPanel.add(totalPanel);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
