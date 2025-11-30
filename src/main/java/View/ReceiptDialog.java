package View;

import InterfaceAdapter.ViewManagerModel;
import InterfaceAdapter.buySell.BuySellState;
import InterfaceAdapter.buySell.BuySellViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReceiptDialog extends JFrame implements PropertyChangeListener {

    JLabel introLabel = new JLabel();
    JLabel descLabel = new JLabel();
    JLabel priceLabel = new JLabel();
    JLabel totalPriceLabel = new JLabel();
    JButton closeButton = new JButton("Close");
    JLabel titleLabel = new JLabel();
    JLabel errorLabel = new JLabel();

    public ReceiptDialog(Frame parentFrame, BuySellViewModel buySellViewModel) {
        setVisible(false);
        setLocationRelativeTo(parentFrame);
        setMinimumSize(new Dimension(300,200));
        buySellViewModel.addPropertyChangeListener(this);

        JPanel detailsBox = new JPanel();
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
            titleLabel.setText("Order Filled");
            final BuySellState state = (BuySellState) evt.getNewValue();
            String symbol = state.getSymbol();
            String amount = String.valueOf(state.getAmount());
            String price = "$" + state.getPrice();
            String totalPrice = "$" + state.getTotalPrice();

            String orderNoun;
            String orderVerb;
            String orderState;
            if (state.getOrder().equals('b')) {
                orderNoun = "Purchase";
                orderVerb = "bought";
                orderState = "cost";
            } else {
                orderNoun = "Sale";
                orderVerb = "sold";
                orderState = "revenue";
            }

            introLabel.setText(orderNoun + " Successful!");
            descLabel.setText(amount + " share(s) of " + symbol + " have been " + orderVerb + ".");
            priceLabel.setText("Price per share: " + price);
            errorLabel.setText(state.getErrorMessage());
            totalPriceLabel.setText("Total " + orderState + ": " + totalPrice);
            closeButton.addActionListener(e -> {
                dispose();
            });
            this.setVisible(true);
        }
        if (evt.getPropertyName().equals("error")){
            final BuySellState state = (BuySellState) evt.getNewValue();
            introLabel.setText("");
            descLabel.setText("");
            priceLabel.setText("");
            totalPriceLabel.setText("");
            errorLabel.setText(state.getErrorMessage());
            this.setVisible(true);
        }
    }
}
