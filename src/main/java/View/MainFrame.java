package View;
import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create frame
            JFrame frame = new JFrame("Stock Trading Simulator");
            frame.setMinimumSize(new java.awt.Dimension(300, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Generate cards
            new StockCard();
            JPanel stockCard = StockCard.initStockCard();

            // Add Tabbed Pane
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Stock", stockCard);

            // Pack contents into layout
            frame.pack();
            frame.setVisible(true);

        });
    }
}