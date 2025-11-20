package View;

import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.stock.StockController;
import InterfaceAdapter.stock.StockState;
import InterfaceAdapter.stock.StockViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockCard {

    public static JPanel initStockCard(JFrame frame) {

        // Call controller to set information in view model
        StockController stockController = new StockController();
        String currentSymbol = "AMZN"; // Change this once I find a way to grab the current symbol from search
        stockController.fetchStockInfo(currentSymbol);
        stockController.fetchBalance();

        // Retrieve stock information
        StockViewModel stockViewModel = new StockViewModel();
        StockState currentState = stockViewModel.getState();

        String stockName = currentState.getStockName();
        String stockDetails = currentState.getStockDetails();
        Double stockPrice = currentState.getStockPrice();
        Integer ownedStock = currentState.getOwnedStock();

        // Retrieve portfolio information
        PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
        Double balance = portfolioViewModel.getState().getBalance();

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
