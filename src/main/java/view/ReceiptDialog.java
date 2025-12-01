package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import interface_adapter.buySell.BuySellState;
import interface_adapter.buySell.BuySellViewModel;

public class ReceiptDialog extends JFrame implements PropertyChangeListener {

    private final JLabel introLabel = new JLabel();
    private final JLabel descLabel = new JLabel();
    private final JLabel priceLabel = new JLabel();
    private final JLabel totalPriceLabel = new JLabel();
    private final JButton closeButton = new JButton("Close");
    private final JLabel titleLabel = new JLabel();
    private final JLabel errorLabel = new JLabel();

    public ReceiptDialog(Frame parentFrame, BuySellViewModel buySellViewModel) {
        setVisible(false);
        this.setAlwaysOnTop(true);
        setLocationRelativeTo(parentFrame);
        setMinimumSize(new Dimension(300, 200));
        buySellViewModel.addPropertyChangeListener(this);

        final JPanel detailsBox = new JPanel();
        detailsBox.setLayout(new BoxLayout(detailsBox, BoxLayout.Y_AXIS));
        detailsBox.setBorder(new EmptyBorder(0, 20, 0, 0));
        detailsBox.add(descLabel);
        detailsBox.add(priceLabel);
        detailsBox.add(totalPriceLabel);
        detailsBox.add(errorLabel);

        add(introLabel);
        add(titleLabel);
        add(detailsBox);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            this.setVisible(true);
            titleLabel.setText("Order Filled");
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
                orderState = "cost";
            }
            else {
                orderNoun = "Sale";
                orderVerb = "sold";
                orderState = "revenue";
            }

            introLabel.setText(orderNoun + " Successful!");
            descLabel.setText(amount + " share(s) of " + symbol + " have been " + orderVerb + ".");
            priceLabel.setText("Price per share: " + price);
            errorLabel.setText(state.getErrorMessage());
            totalPriceLabel.setText("Total " + orderState + ": " + totalPrice);
            closeButton.addActionListener(event -> {
                dispose();
            });
        }
        if (evt.getPropertyName().equals("error")) {
            this.setVisible(true);
            final BuySellState state = (BuySellState) evt.getNewValue();
            introLabel.setText("");
            descLabel.setText("");
            priceLabel.setText("");
            totalPriceLabel.setText("");
            errorLabel.setText(state.getErrorMessage());
        }
    }
}
