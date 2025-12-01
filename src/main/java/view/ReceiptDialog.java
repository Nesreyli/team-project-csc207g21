package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import interface_adapter.buySell.BuySellState;
import interface_adapter.buySell.BuySellViewModel;

/**
 * A dialog that displays purchase/sale data as a digital receipt after a stock purchase/sale is processed.
 */

public class ReceiptDialog extends JDialog implements PropertyChangeListener {

    private final JLabel introLabel = new JLabel("Purchase/Sale Success!");
    private final JLabel descLabel = new JLabel("Description");
    private final JLabel priceLabelLeft = new JLabel("Price / Share:");
    private final JLabel priceLabelRight = new JLabel("$XX.XX");
    private final JLabel totalPriceLabelLeft = new JLabel("Total Cost/Revenue:");
    private final JLabel totalPriceLabelRight = new JLabel("$XX.XX");
    private final JPanel detailsBox = new JPanel();
    private final JLabel titleLabel = new JLabel();
    private final JLabel errorLabel = new JLabel();
    private final JButton closeButton = new JButton("Close");

    public ReceiptDialog(Frame parentFrame, BuySellViewModel buySellViewModel) {
        setVisible(false);
        this.setAlwaysOnTop(true);
        setLocationRelativeTo(parentFrame);
        setMinimumSize(new Dimension(300, 200));
        buySellViewModel.addPropertyChangeListener(this);

        Font currentFont = introLabel.getFont();
        Font h1Font = new Font(currentFont.getName(), Font.BOLD, 26);
        Font h2Font = new Font(currentFont.getName(), Font.BOLD, 20);
        Font bFont = new Font(currentFont.getName(), Font.BOLD, 18);
        Font pFont = new Font(currentFont.getName(), currentFont.getStyle(), 18);

        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setFont(h1Font);

        JPanel introPanel = new JPanel();
        introPanel.setLayout(new GridBagLayout());
        introPanel.add(introLabel);
        introPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        titleLabel.setFont(h2Font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(titleLabel);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Desc Panel
        descLabel.setFont(pFont);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        descLabel.setForeground(Color.decode("#333333"));
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.decode("#E0E0E0"));
        descPanel.add(descLabel, BorderLayout.WEST);
        descPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Price Panel
        JPanel pricePanel = new JPanel(new BorderLayout());
        pricePanel.add(priceLabelLeft, BorderLayout.WEST);
        pricePanel.add(priceLabelRight, BorderLayout.EAST);
        pricePanel.setBackground(Color.decode("#E0E0E0"));
        pricePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Total Price Panel
        JPanel totalPricePanel = new JPanel(new BorderLayout());
        totalPricePanel.add(totalPriceLabelLeft, BorderLayout.WEST);
        totalPricePanel.add(totalPriceLabelRight, BorderLayout.EAST);
        totalPricePanel.setBackground(Color.decode("#E0E0E0"));
        totalPricePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        priceLabelLeft.setFont(bFont);
        priceLabelLeft.setForeground(Color.decode("#4A4A4A"));
        priceLabelRight.setFont(pFont);
        totalPriceLabelLeft.setFont(bFont);
        totalPriceLabelLeft.setForeground(Color.decode("#4A4A4A"));
        totalPriceLabelRight.setFont(pFont);

        // Details Box
        detailsBox.setVisible(true);
        detailsBox.setLayout(new BoxLayout(detailsBox, BoxLayout.Y_AXIS));
        detailsBox.setBorder(new EmptyBorder(15, 15, 15, 15));
        detailsBox.setBackground(Color.decode("#E0E0E0"));
        detailsBox.add(descPanel);
        detailsBox.add(Box.createVerticalStrut(5));
        detailsBox.add(pricePanel);
        detailsBox.add(Box.createVerticalStrut(5));
        detailsBox.add(totalPricePanel);

        // Error Message
        errorLabel.setFont(bFont);
        errorLabel.setForeground(Color.decode("#4A4A4A"));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridBagLayout());
        errorPanel.add(errorLabel);
        errorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(closeButton);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        closeButton.setFont(pFont);
        closeButton.setBackground(Color.decode("#4A4A4A"));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(introPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(detailsBox);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(errorPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
        setMinimumSize(new Dimension(600, 500));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            this.setVisible(true);
            titleLabel.setText("The following order has been filled:");
            final BuySellState state = (BuySellState) evt.getNewValue();
            final String symbol = state.getSymbol();
            final String amount = String.valueOf(state.getAmount());
            final String price = "$" + state.getPrice();
            final String totalPrice = "$" + state.getTotalPrice();

            final String orderNoun;
            final String orderVerb;
            final String orderState;
            if (state.getOrder().equals('b')) {
                orderNoun = "Purchase";
                orderVerb = "bought";
                orderState = "Cost";
            }
            else {
                orderNoun = "Sale";
                orderVerb = "sold";
                orderState = "Revenue";
            }

            introLabel.setText(orderNoun + " Successful!");
            introLabel.setForeground(Color.decode("#388E3C"));
            descLabel.setText(amount + " share(s) of " + symbol + " have been " + orderVerb + ".");
            priceLabelRight.setText(price);
            totalPriceLabelLeft.setText("Total " + orderState);
            totalPriceLabelRight.setText(totalPrice);
            detailsBox.setVisible(true);
            errorLabel.setText(state.getErrorMessage());
            closeButton.addActionListener(event -> {
                dispose();
            });
        }
        if (evt.getPropertyName().equals("error")) {
            this.setVisible(true);
            final BuySellState state = (BuySellState) evt.getNewValue();
            introLabel.setText("Transaction Failed");
            introLabel.setForeground(Color.decode("#C62828"));
            titleLabel.setText("Your transaction failed to process due to an error.");
            detailsBox.setVisible(false);
            errorLabel.setText(state.getErrorMessage());
            closeButton.addActionListener(event -> {
                dispose();
            });
        }
    }
}
