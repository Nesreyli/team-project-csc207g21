package View;

import InterfaceAdapter.stock.StockController;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class StockDialog extends JDialog {
    public StockDialog(Frame frame, boolean isBuy, String stockName,
                       String stockDetails, BigDecimal stockPrice, BigDecimal balance) {
        super(frame, "Stock Trading", true);

        String keyword;
        if (isBuy) {
            keyword = "Buy";
        } else {
            keyword = "Sell";
        }

        JLabel label = new JLabel(stockName + " Stock");


        // (Actions) Select Panel
        JLabel selectLabel = new JLabel("Shares to " + keyword + ": ");
        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
        selectPanel.add(selectLabel);


        // (Actions) (Select) Plus/Minus Panel
        JPanel plusMinusPanel = new JPanel();
        plusMinusPanel.setLayout(new BoxLayout(plusMinusPanel, BoxLayout.X_AXIS));
        JButton addButton = new JButton("+");
        JButton minusButton = new JButton("-");
        JTextField field = new JTextField("1");

        plusMinusPanel.add(addButton);
        plusMinusPanel.add(field);
        plusMinusPanel.add(minusButton);

        selectPanel.add(plusMinusPanel);


        // (Actions) Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton okButton = new JButton(keyword);
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);


        // Actions Panel
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.X_AXIS));
        actionsPanel.add(selectPanel);
        actionsPanel.add(buttonPanel);


        // Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(new JLabel(stockDetails));
        JLabel priceLabel =
                new JLabel("Estimate: " + calculateTotal(stockPrice.doubleValue(), "1"));
        detailsPanel.add(priceLabel);


        // EVENT LISTENERS
        addButton.addActionListener(e -> {
            field.setText(String.valueOf(Integer.parseInt(field.getText()) + 1));
            priceLabel.setText("Estimate: $" + calculateTotal(stockPrice.doubleValue(), field.getText()));
        });

        minusButton.addActionListener(e -> {
            if (Integer.parseInt(field.getText()) >= 2) {
                field.setText(String.valueOf(Integer.parseInt(field.getText()) - 1));
                priceLabel.setText("Estimate: $" + calculateTotal(stockPrice.doubleValue(), field.getText()));
            }
        });

        field.addActionListener(e -> {
            priceLabel.setText("Estimate: $" + calculateTotal(stockPrice.doubleValue(), field.getText()));
        });

        okButton.addActionListener(e -> {
            if (isBuy && (new BigDecimal(field.getText()).multiply(stockPrice)).compareTo(balance) <= 0) {

                // TODO: Buy use case

                System.out.println("Purchase");
                dispose();
            } else if (isBuy) {
                JOptionPane.showMessageDialog(frame,
                        "You do not have enough money in your balance to make this purchase!",
                        "Insufficient Funds",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                // TODO: Sell use case

                System.out.println("Sale");
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        // Layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(label);
        add(actionsPanel);
        add(detailsPanel);
        setMinimumSize(new Dimension(600, 400));
    }

    public String calculateTotal(Double stockPrice, String quantityText) {
        Integer quantity = Integer.parseInt(quantityText);
        return String.format("%.2f", stockPrice * quantity);
    }
}