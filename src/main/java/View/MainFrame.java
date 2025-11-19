package View;

import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create frame
            JFrame frame = new JFrame("Stock Trading Simulator");
            frame.setMinimumSize(new java.awt.Dimension(600, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Generate cards
            new StockCard();
            JPanel stockCard = StockCard.initStockCard(frame);

            // Add Tabbed Pane
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Stock", stockCard);

            // Pack contents into layout
            frame.add(tabbedPane);
            frame.add(stockCard);
            frame.pack();
            frame.setVisible(true);

        });
    }
}