package View;

import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PriceState;
import InterfaceAdapter.stock_price.PriceViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StockPriceView extends JFrame implements ActionListener, PropertyChangeListener {
    private final String viewName = "Stock Price";
    private PriceViewModel priceViewModel;
    private PriceController priceController;

    private final JLabel symbol;
    private final JLabel company;
    private final JLabel country;
    private final JLabel price;
    private final JLabel ytdPrice;
    private final JLabel ytdPerformance;
    private final JButton buy;
    private final JButton sell;

    public StockPriceView(PriceViewModel priceViewModel){
        this.setVisible(false);

        this.priceViewModel = priceViewModel;
        priceViewModel.addPropertyChangeListener(this);

        symbol = new JLabel();
        company = new JLabel();
        country = new JLabel();
        price = new JLabel();
        ytdPrice = new JLabel();
        ytdPerformance = new JLabel();
        buy = new JButton("Buy");
        sell = new JButton("sell");
        this.setTitle("Stock Details");
        this.setSize(700, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(240, 248, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15,15));

        symbol.setFont(new Font("Arial", Font.BOLD, 32));
        symbol.setAlignmentX(Component.LEFT_ALIGNMENT);

        company.setFont(new Font("Arial", Font.PLAIN, 18));
        company.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(symbol);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(company);

        price.setFont(new Font("Arial", Font.PLAIN, 20));
        price.setAlignmentX(Component.RIGHT_ALIGNMENT);

        ytdPerformance.setFont(new Font("Arial", Font.PLAIN, 16));
        ytdPerformance.setAlignmentX(Component.RIGHT_ALIGNMENT);

        ytdPrice.setFont(new Font("Arial", Font.PLAIN, 10));
        ytdPrice.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 20, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel row = new JPanel();
        row.add(infoPanel.add(price));

        addInfoRow(infoPanel, "Country", country.getText());
        addInfoRow(infoPanel, "Company", company.getText());

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        // Buy
        buy.setBackground(new Color(76, 175, 80));
        buy.setForeground(Color.BLACK);
        buy.setFocusPainted(false);
        buy.setFont(new Font("Arial", Font.BOLD, 14));
        buy.setPreferredSize(new Dimension(120, 40));
        buy.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Buy functionality would redirect for" + symbol);
            // Implement buy functionality somewhere or import from Lucas one
            // SearchController.navigateToBuyPage(symbol);
        });

        // Sell
        sell.setBackground(new Color(244, 67, 54));
        sell.setForeground(Color.BLACK);
        sell.setFocusPainted(false);
        sell.setFont(new Font("Arial", Font.BOLD, 14));
        sell.setPreferredSize(new Dimension(120, 40));
        sell.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sell functionality would redirect for" + symbol);
            // Implement sell functionality somewhere or import from Lucas one
            // SearchController.navigateToSellPage(symbol);

        });

        // Close
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> this.dispose());

        // Assemble Button Panel
        buttonPanel.add(buy);
        buttonPanel.add(sell);
        buttonPanel.add(closeButton);

        // Assemble main frame
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 15));
        labelComponent.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 15));

        panel.add(labelComponent);
        panel.add(valueLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setVisible(true);

        final PriceState priceState = (PriceState) evt.getNewValue();
        symbol.setText(priceState.getSymbol());
        company.setText(priceState.getCompany());
        ytdPrice.setText(priceState.getYtdPrice());
        ytdPerformance.setText(priceState.getYtdPerformance());
        country.setText(priceState.getCountry());

    }

    public String getViewName() {
        return viewName;
    }

    public void setPriceController(PriceController priceController){
        this.priceController = priceController;
    }
}
