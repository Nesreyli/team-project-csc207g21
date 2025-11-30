package View;

import InterfaceAdapter.buySell.BuySellState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReceiptDialog extends JDialog implements PropertyChangeListener {

    JLabel introLabel = new JLabel("__ Success!");
    JLabel descLabel = new JLabel("Description");
    JLabel priceLabel = new JLabel("Price per share: $XX.XX");
    JLabel totalPriceLabel = new JLabel("Total __: $XX.XX");
    JButton closeButton = new JButton("Close");

    public ReceiptDialog(Frame parentFrame) {
        super(parentFrame, "Receipt", true);
        JLabel titleLabel = new JLabel("Details:");

        JPanel detailsBox = new JPanel();
        detailsBox.setLayout(new BoxLayout(detailsBox, BoxLayout.Y_AXIS));
        detailsBox.setBorder(new EmptyBorder(0, 20, 0, 0));
        detailsBox.add(descLabel);
        detailsBox.add(priceLabel);
        detailsBox.add(totalPriceLabel);

        add(introLabel);
        add(titleLabel);
        add(detailsBox);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final BuySellState state = (BuySellState) evt.getNewValue();
            String symbol = state.getSymbol();
            String amount = String.valueOf(state.getAmount());
            String price = "$" + String.format("%.2f", state.getPrice());
            String totalPrice = "$" + String.format("%.2f", state.getTotalPrice());

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
            descLabel.setText(amount + "share(s) of " + symbol + " have been " + orderVerb + ".");
            priceLabel.setText("Price per share: " + price);
            totalPriceLabel.setText("Total " + orderState + ": " + totalPrice);

            closeButton.addActionListener(e -> {
                dispose();
            });
        }
    }
}
