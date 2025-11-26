package View;

import InterfaceAdapter.stock.StockViewModel;

import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create frame
            JFrame frame = new JFrame("Stock Trading Simulator");
            frame.setMinimumSize(new java.awt.Dimension(600, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Pack contents into layout
            StockViewModel stockVM = new StockViewModel();
            StockView stockView = new StockView(stockVM, frame);

            frame.add(stockView);
            frame.pack();
            frame.setVisible(true);

        });
    }
}