package View;
import javax.swing.*;

public class StockCard {
    public static JPanel initStockCard() {

        // Stock Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("Stock Name"));

        // Stock Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(new JLabel("$XXXX.XX"));

        // Buy
        JButton BuyButton = new JButton("Buy");
        BuyButton.addActionListener(e -> { System.out.println("Buy"); });
        controlPanel.add(BuyButton);

        // Sell
        JButton SellButton = new JButton("Sell");
        BuyButton.addActionListener(e -> { System.out.println("Sell"); });
        controlPanel.add(SellButton);

        // Follow
        JButton FollowButton = new JButton("Follow");
        BuyButton.addActionListener(e -> { System.out.println("Follow"); });
        controlPanel.add(FollowButton);

        JPanel stockCard = new JPanel();
        stockCard.setLayout(new BoxLayout(stockCard, BoxLayout.X_AXIS));
        stockCard.add(infoPanel);
        stockCard.add(controlPanel);

        return stockCard;
    }
}
