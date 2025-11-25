package View;

import InterfaceAdapter.stock.StockController;
import InterfaceAdapter.stock.StockState;
import InterfaceAdapter.stock.StockViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StockView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Stock View";
    private final StockViewModel stockViewModel;
    private StockController stockController;
    private JFrame frame;

    // This will be set by a method
    private String symbol;

    // Elements to be updated
    JLabel nameLabel = new JLabel();
    JLabel countryLabel = new JLabel();
    JLabel priceLabel = new JLabel();
    JButton buyButton = new JButton("Buy");
    JButton sellButton = new JButton("Sell");
    JButton followButton = new JButton("Follow");
    JLabel ownedLabel = new JLabel("You own 0 stocks.");

    // TODO: Get username and password
    String username = "username";
    String password = "password";

    // Initialize elements
    public StockView(StockViewModel stockViewModel, JFrame frame) {

        this.frame = frame;

        this.stockViewModel = stockViewModel;
        this.stockViewModel.addPropertyChangeListener(this);

        // Stock Information Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nameLabel);
        infoPanel.add(countryLabel);

        // Stock Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(priceLabel);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);
        controlPanel.add(ownedLabel);

        // Layout and add elements
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(infoPanel);
        add(controlPanel);
    }

    public void setController(StockController stockController) {
        this.stockController = stockController;
    }

    // This method should be called when a stock is selected from the search results.
    //
    // public void setDetails(String stockSymbol) {
    //    symbol = stockSymbol;
    //    stockController.execute(symbol);
    //}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final StockState state = (StockState) evt.getNewValue();

            // Update name
            String nameAndSymbol = String.format("(%s) %s", symbol, state.getCompany());
            nameLabel.setText(nameAndSymbol);

            // Update country
            String details = "Country: " + state.getCountry();
            nameLabel.setText(details);

            // Update price
            priceLabel.setText("$" + String.format("%.2f", state.getPrice()));

            // Update buy button
            buyButton.addActionListener( e-> {
                    StockDialog stockDialog = new StockDialog(frame,
                            true, nameAndSymbol, details, state.getPrice(), state.getValue());
                    stockDialog.setVisible(true);
            });

            // Update sell button
            sellButton.addActionListener(e -> {
                    StockDialog stockDialog = new StockDialog(frame,
                            false, nameAndSymbol, details, state.getPrice(), state.getValue());
                    stockDialog.setVisible(true);
            });

            // Update follow button
            followButton.addActionListener(e -> {
                System.out.println("Follow");
            });
        }
    }
}
