package View;

import InterfaceAdapter.stock.StockController;
import InterfaceAdapter.stock.StockState;
import InterfaceAdapter.stock.StockViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

public class StockView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Stock View";
    private final StockViewModel stockViewModel;
    private StockController stockController;
    private JFrame frame;

    // This will be set by a method
    private String symbol;

    // Elements to be updated
    JLabel nameLabel = new JLabel("(XXXX) Sample Company");
    JLabel countryLabel = new JLabel("Bezosland");
    JLabel priceLabel = new JLabel("$0.00");
    JButton buyButton = new JButton("Buy");
    JButton sellButton = new JButton("Sell");
    JButton followButton = new JButton("Follow");
    JButton addButton = new JButton("+");
    JButton minusButton = new JButton("-");
    JTextField textField = new JTextField("1");
    JLabel estimateLabel = new JLabel("Estimate: $0.00");
    JLabel ownedLabel = new JLabel("You own 0 stocks.");

    // Initialize elements
    public StockView(StockViewModel stockViewModel, JFrame frame) {

        this.frame = frame;

        this.stockViewModel = stockViewModel;
        this.stockViewModel.addPropertyChangeListener(this);

        // Stock Information Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nameLabel);
        infoPanel.add(countryLabel);

        // Stock Control Panel >> Top Buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(priceLabel);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);

        // Stock Control Panel >> Plus Minus Buttons
        JPanel plusMinusPanel = new JPanel();
        plusMinusPanel.setLayout(new BoxLayout(plusMinusPanel, BoxLayout.X_AXIS));
        plusMinusPanel.add(addButton);
        plusMinusPanel.add(textField);
        textField.setMaximumSize(new Dimension(80, 30));
        textField.setColumns(5);
        plusMinusPanel.add(minusButton);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(plusMinusPanel);

        // Stock Control Panel >> Button Labels
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(estimateLabel);
        controlPanel.add(ownedLabel);

        // Layout and add elements
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        infoPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        add(infoPanel);
        add(controlPanel);
    }

    public String calculateTotal(BigDecimal stockPrice, String quantityText) {
        int quantity = Integer.parseInt(quantityText);
        return String.format("%.2f", stockPrice.multiply(BigDecimal.valueOf(quantity)));
    }

    public void setController(StockController stockController) {
        this.stockController = stockController;
    }

    // This method should be called when a stock is selected from the search results.
    //
    // public void setDetails(String stockSymbol) {
    //    username = ???
    //    password = ???
    //    symbol = stockSymbol;
    //    stockController.execute(symbol, username, password);
    //}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final StockState state = (StockState) evt.getNewValue();
            BigDecimal stockPrice = state.getPrice();

            // Update name
            String nameAndSymbol = String.format("(%s) %s", symbol, state.getCompany());
            nameLabel.setText(nameAndSymbol);

            // Update country
            String details = "Country: " + state.getCountry();
            nameLabel.setText(details);

            // Update price
            priceLabel.setText("Price per share: $" + String.format("%.2f", state.getPrice()));

            // Update follow button
            followButton.addActionListener(e -> {
                System.out.println("Follow");
            });

            // Add button
            addButton.addActionListener(e -> {
                textField.setText(String.valueOf(Integer.parseInt(textField.getText()) + 1));
                priceLabel.setText("Estimate: $" + calculateTotal(stockPrice, textField.getText()));
            });

            // Minus button
            minusButton.addActionListener(e -> {
                if (Integer.parseInt(textField.getText()) >= 2) {
                    textField.setText(String.valueOf(Integer.parseInt(textField.getText()) - 1));
                    priceLabel.setText("Estimate: $" + calculateTotal(stockPrice, textField.getText()));
                }
            });

            // Add/minus text field
            textField.addActionListener(e -> {
                priceLabel.setText("Estimate: $" + calculateTotal(stockPrice, textField.getText()));
            });

            // Buy button
            buyButton.addActionListener(e -> {
                if (new BigDecimal(textField.getText()).multiply(stockPrice)
                        .compareTo(state.getValue()) <= 0) {

                    int confirmation = JOptionPane.showConfirmDialog(frame,
                            "Confirm Purchase?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        System.out.println("Purchase");
                    }

                } else {
                    JOptionPane.showMessageDialog(frame,
                            "You do not have enough money in your balance to make this purchase!",
                            "Insufficient Funds",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Sell button
            sellButton.addActionListener(e -> {
                int confirmation = JOptionPane.showConfirmDialog(frame,
                        "Confirm Sale?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    System.out.println("Sale");
                }
            });


        }
    }
}
