package View;

import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PriceState;
import InterfaceAdapter.stock_price.PriceViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StockPriceView extends JFrame implements ActionListener, PropertyChangeListener {
    private final String viewName = "Stock Price";
    private PriceViewModel priceViewModel;
    private PriceController priceController;

    private final JLabel symbol;
    private final JLabel company;
    private final JLabel country;
    private final JLabel price;
    private final JLabel ytdPrice;
    private final JLabel ytdPerformance;
    private final JButton buy;
    private final JButton sell;

    // Additional labels for info panel
    private JLabel companyValue;
    private JLabel symbolValue;

    public StockPriceView(PriceViewModel priceViewModel){
        this.setVisible(false);

        this.priceViewModel = priceViewModel;
        priceViewModel.addPropertyChangeListener(this);

        symbol = new JLabel();
        company = new JLabel();
        country = new JLabel();
        price = new JLabel();
        ytdPrice = new JLabel();
        ytdPerformance = new JLabel();
        buy = new JButton("Buy Stock");
        sell = new JButton("Sell Stock");

        this.setTitle("Stock Details");
        this.setSize(700, 450);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Header Panel - Contains symbol/company on left and price info on right
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout(15, 15));
        headerPanel.setBackground(new Color(240, 248, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15,15));

        // Left side - Symbol and Company
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

        // Right side - Price information
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

        // Info Panel - Display additional details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2, 20, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create static labels for display
        JLabel countryLabelTitle = new JLabel("Country:");
        countryLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        countryLabelTitle.setForeground(Color.DARK_GRAY);

        JLabel companyLabelTitle = new JLabel("Company:");
        companyLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        companyLabelTitle.setForeground(Color.DARK_GRAY);

        JLabel symbolLabelTitle = new JLabel("Symbol:");
        symbolLabelTitle.setFont(new Font("Arial", Font.BOLD, 14));
        symbolLabelTitle.setForeground(Color.DARK_GRAY);

        // Set font for value labels
        country.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel companyValue = new JLabel();
        companyValue.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel symbolValue = new JLabel();
        symbolValue.setFont(new Font("Arial", Font.BOLD, 14));

        // Add rows to info panel
        infoPanel.add(countryLabelTitle);
        infoPanel.add(country);
        infoPanel.add(companyLabelTitle);
        infoPanel.add(companyValue);
        infoPanel.add(symbolLabelTitle);
        infoPanel.add(symbolValue);

        // Store references for updates
        this.companyValue = companyValue;
        this.symbolValue = symbolValue;

        // Action Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        // Buy Button
        buy.setBackground(new Color(76, 175, 80));
        buy.setForeground(Color.WHITE);
        buy.setFocusPainted(false);
        buy.setFont(new Font("Arial", Font.BOLD, 14));
        buy.setPreferredSize(new Dimension(120, 40));
        buy.addActionListener(e -> {
            showBuyDialog();
        });

        // Sell Button
        sell.setBackground(new Color(244, 67, 54));
        sell.setForeground(Color.WHITE);
        sell.setFocusPainted(false);
        sell.setFont(new Font("Arial", Font.BOLD, 14));
        sell.setPreferredSize(new Dimension(120, 40));
        sell.addActionListener(e -> {
            showSellDialog();
        });

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> this.dispose());

        // Assemble Button Panel
        buttonPanel.add(buy);
        buttonPanel.add(sell);
        buttonPanel.add(closeButton);

        // Assemble main frame
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }


    /**
     * Shows buy dialog for the stock
     */
    private void showBuyDialog() {
        final PriceState state = priceViewModel.getState();

        JDialog buyDialog = new JDialog(this, "Buy " + state.getSymbol(), true);
        buyDialog.setSize(400, 250);
        buyDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Buy " + state.getSymbol());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel("Current Price: " + state.getPrice());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(15));

        // Amount Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount (shares):");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField amountField = new JTextField(10);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        infoPanel.add(inputPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton confirmButton = new JButton("Confirm Buy");
        confirmButton.setBackground(new Color(76, 175, 80));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.addActionListener(e -> {
            String amount = amountField.getText().trim();
            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(buyDialog,
                        "Please enter an amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int shares = Integer.parseInt(amount);
                if (shares <= 0) {
                    JOptionPane.showMessageDialog(buyDialog,
                            "Amount must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // TODO: Get username/password from logged in state
                // executeBuyOrder(state.getSymbol(), amount, username, password, buyDialog);
                JOptionPane.showMessageDialog(buyDialog,
                        "Buy functionality: " + shares + " shares of " + state.getSymbol());
                buyDialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(buyDialog,
                        "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.addActionListener(e -> buyDialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        buyDialog.add(mainPanel);
        buyDialog.setVisible(true);
    }

    /**
     * Shows sell dialog for the stock
     */
    private void showSellDialog() {
        final PriceState state = priceViewModel.getState();

        JDialog sellDialog = new JDialog(this, "Sell " + state.getSymbol(), true);
        sellDialog.setSize(400, 250);
        sellDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sell " + state.getSymbol());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel("Current Price: " + state.getPrice());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(15));

        // Amount Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount (shares):");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField amountField = new JTextField(10);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        infoPanel.add(inputPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton confirmButton = new JButton("Confirm Sell");
        confirmButton.setBackground(new Color(244, 67, 54));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.addActionListener(e -> {
            String amount = amountField.getText().trim();
            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(sellDialog,
                        "Please enter an amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int shares = Integer.parseInt(amount);
                if (shares <= 0) {
                    JOptionPane.showMessageDialog(sellDialog,
                            "Amount must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // TODO: Get username/password from logged in state
                // executeSellOrder(state.getSymbol(), amount, username, password, sellDialog);
                JOptionPane.showMessageDialog(sellDialog,
                        "Sell functionality: " + shares + " shares of " + state.getSymbol());
                sellDialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(sellDialog,
                        "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.addActionListener(e -> sellDialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        sellDialog.add(mainPanel);
        sellDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            this.setVisible(true);

            final PriceState priceState = (PriceState) evt.getNewValue();

            // Update header labels
            symbol.setText(priceState.getSymbol());
            company.setText(priceState.getCompany());

            // Update info panel labels
            country.setText(priceState.getCountry());
            companyValue.setText(priceState.getCompany());
            symbolValue.setText(priceState.getSymbol());

            // Format and display price
            price.setText(priceState.getPrice());

            // Format and display YTD info
            ytdPrice.setText("YTD Open: " + priceState.getYtdPrice());

            // Color code the performance
            String perfText = priceState.getYtdPerformance();
            ytdPerformance.setText(perfText);

            // Set color based on positive/negative performance
            if (perfText != null && !perfText.isEmpty()) {
                try {
                    // Remove % sign if present and parse
                    String numericPart = perfText.replace("%", "").trim();
                    double perfValue = Double.parseDouble(numericPart);

                    // Add % symbol if not already present
                    String displayText = perfText.contains("%") ? perfText : perfText + "%";
                    ytdPerformance.setText(displayText);

                    if (perfValue >= 0) {
                        ytdPerformance.setForeground(new Color(76, 175, 80)); // Green
                    } else {
                        ytdPerformance.setForeground(new Color(244, 67, 54)); // Red
                    }
                } catch (NumberFormatException e) {
                    ytdPerformance.setText(perfText);
                    ytdPerformance.setForeground(Color.BLACK);
                }
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setPriceController(PriceController priceController){
        this.priceController = priceController;
    }
}