package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockCard {

    public static JPanel initStockCard(JFrame frame) {

        // Retrieve Information
        String stockName = "Amazon";
        String stockDetails = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                "eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        Double stockPrice = 22.2;
        Integer balance = 500;
        Integer ownedStock = 1;

        // Stock Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(stockName));

        // Stock Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(new JLabel("$" + String.format("%.2f", stockPrice)));

        // Buy
        JButton BuyButton = new JButton("Buy");
        BuyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockDialog stockDialog = new StockDialog(frame, true, stockName, stockDetails, stockPrice, balance);
                stockDialog.setVisible(true);
            }
        });
        controlPanel.add(BuyButton);

        // Sell
        JButton SellButton = new JButton("Sell");
        SellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockDialog stockDialog = new StockDialog(frame, false, stockName, stockDetails, stockPrice, balance);
                stockDialog.setVisible(true);
            }
        });
        controlPanel.add(SellButton);

        // Follow
        JButton FollowButton = new JButton("Follow");
        FollowButton.addActionListener(e -> { System.out.println("Follow"); });
        controlPanel.add(FollowButton);

        JLabel ownedLabel = new JLabel("Owned: " + ownedStock);
        controlPanel.add(ownedLabel);

        JPanel stockCard = new JPanel();
        stockCard.setLayout(new BoxLayout(stockCard, BoxLayout.X_AXIS));
        stockCard.add(infoPanel);
        stockCard.add(controlPanel);

        return stockCard;
    }
}
