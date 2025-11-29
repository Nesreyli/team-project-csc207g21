package View;

import Entity.Stock_Search;
import InterfaceAdapter.Stock_Search.SearchController;
import InterfaceAdapter.Stock_Search.SearchState;
import InterfaceAdapter.Stock_Search.SearchViewModel;
import InterfaceAdapter.stock_price.PriceController;
import InterfaceAdapter.stock_price.PriceViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.Map;

/**
 * The View for Stock Search functionality with clickable stock symbols..
 */
public class SearchView extends JPanel implements  ActionListener, PropertyChangeListener {
    private JPanel location = this;
    private final String viewName = "Stock Search";
    private final SearchViewModel searchViewModel;
    private SearchController SearchController;
    private PriceController priceController;
    private StockPriceView stockPriceView;

    //UI Stuff
    private final JTextField searchField = new JTextField(20);
    private final JButton searchButton;
    private final JButton loadallButton;
    private final JLabel errorLabel;
    private final JTable resultsTable;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private final JLabel statusLabel = new JLabel("Ready");
    private final JButton previous;


    public SearchView(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);
        // Storing the stock easier
        this.searchViewModel.addPropertyChangeListener(this);

        previous = new JButton("<");
        previous.addActionListener(this);
        // UI Component
        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        searchButton.addActionListener(this);
        loadallButton = new JButton(SearchViewModel.LOAD_ALL_BUTTON_LABEL);
        loadallButton.addActionListener(this);

        String[] columnNames = {"Symbol", "Company", "Price (CAD)", "Country"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom Render to make Symbol Column look like a link
        resultsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setForeground(Color.GREEN.darker());
                setFont(getFont().deriveFont(Font.BOLD));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setText("<html><u>" + value + "</u></html>");
                return c;
            }
        });

        // Clicking capability
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int row = resultsTable.rowAtPoint(e.getPoint());
                int column = resultsTable.columnAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    String symbol = (String) resultsTable.getValueAt(row, 0);
                    // new Stock Price tab
                    priceController.executePrice(symbol);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e){
                int column = resultsTable.columnAtPoint(e.getPoint());
                if (column >= 0) {
                    resultsTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e){
                resultsTable.setCursor(Cursor.getDefaultCursor());
            }
        });

        // Sorting Capability
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);

        scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Layout
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.LIGHT_GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.LIGHT_GRAY);
        JLabel title = new JLabel(SearchViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.LIGHT_GRAY);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel searchLabel = new JLabel(SearchViewModel.SEARCH_FIELD_LABEL + ":");
        searchPanel.add(previous);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(loadallButton);

        // Error Style
        errorLabel = new JLabel();

        errorLabel.setForeground(Color.RED);
        searchPanel.add(errorLabel);

        // Centering
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.add(statusLabel);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);

        // Add document listener for real-time search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SearchState currentState = searchViewModel.getState();
                currentState.setSearchQuery(searchField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Add Enter key support for search field
        searchField.addActionListener(e -> performSearch());
    }

    // Show detailed stock information page when a symbol is clicked
//    private void showStockInfo(String symbol) {
//        Map currentStocks = searchViewModel.getState().getSearchResults();
//        if (currentStocks != null && currentStocks.containsKey(symbol)) {
//            Stock_Search stock = (Stock_Search) currentStocks.get(symbol);
//
//            // Stock details UI
//            JFrame stockFrame = new JFrame(symbol + " - Stock Details");
//            stockFrame.setSize(700, 400);
//            stockFrame.setLocationRelativeTo(this);
//
//            JPanel mainPanel = new JPanel();
//            mainPanel.setLayout(new BorderLayout(10,10));
//            mainPanel.setBackground(Color.WHITE);
//            mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//
//            // Header Panel
//            JPanel headerPanel = new JPanel();
//            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
//            headerPanel.setBackground(new Color(240, 248, 255));
//            headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15,15));
//
//            JLabel symbolLabel = new JLabel(stock.getSymbol());
//            symbolLabel.setFont(new Font("Arial", Font.BOLD, 32));
//            symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//            JLabel companyLabel = new JLabel(stock.getCompany());
//            companyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
//            companyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//            headerPanel.add(symbolLabel);
//            headerPanel.add(Box.createVerticalStrut(5));
//            headerPanel.add(companyLabel);
//
//            // Info Panel
//            JPanel infoPanel = new JPanel();
//            infoPanel.setLayout(new GridLayout(0, 2, 20, 15));
//            infoPanel.setBackground(Color.WHITE);
//            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
//
//            addInfoRow(infoPanel, "Current Price:", "S" + formatPrice(stock.getPrice()));
//            addInfoRow(infoPanel, "Country", stock.getCountry());
//            addInfoRow(infoPanel, "Company", stock.getCompany());
//
//            // Action Buttons
//            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
//            buttonPanel.setBackground(Color.WHITE);
//
//            // Buy
//            JButton buyButton = new JButton("Buy Stock!");
//            buyButton.setBackground(new Color(76, 175, 80));
//            buyButton.setForeground(Color.WHITE);
//            buyButton.setFocusPainted(false);
//            buyButton.setFont(new Font("Arial", Font.BOLD, 14));
//            buyButton.setPreferredSize(new Dimension(120, 40));
//            buyButton.addActionListener(e -> {
//                JOptionPane.showMessageDialog(stockFrame, "Buy functionality would redirect for" + symbol);
//                // Implement buy functionality somewhere or import from Lucas one
//                // SearchController.navigateToBuyPage(symbol);
//            });
//
//            // Sell
//            JButton sellButton = new JButton("Sell Stock!");
//            sellButton.setBackground(new Color(244, 67, 54));
//            sellButton.setForeground(Color.WHITE);
//            sellButton.setFocusPainted(false);
//            sellButton.setFont(new Font("Arial", Font.BOLD, 14));
//            sellButton.setPreferredSize(new Dimension(120, 40));
//            sellButton.addActionListener(e -> {
//                JOptionPane.showMessageDialog(stockFrame, "Sell functionality would redirect for" + symbol);
//                // Implement sell functionality somewhere or import from Lucas one
//                // SearchController.navigateToSellPage(symbol);
//
//            });
//
//            // Close
//            JButton closeButton = new JButton("Close");
//            closeButton.setBackground(Color.LIGHT_GRAY);
//            closeButton.setFocusPainted(false);
//            closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
//            closeButton.setPreferredSize(new Dimension(120, 40));
//            closeButton.addActionListener(e -> stockFrame.dispose());
//
//            // Assemble Button Panel
//            buttonPanel.add(buyButton);
//            buttonPanel.add(sellButton);
//            buttonPanel.add(closeButton);
//
//            // Assemble main frame
//            mainPanel.add(headerPanel, BorderLayout.NORTH);
//            mainPanel.add(infoPanel, BorderLayout.CENTER);
//            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//            stockFrame.add(mainPanel);
//            stockFrame.setVisible(true);
//
//        }
//    }

    // Helper method to add info rows to info panel
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
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(searchButton)) {
            performSearch();
        } else if (evt.getSource().equals(loadallButton)) {
            performLoadAll();
        } else if(evt.getSource().equals(previous)){
            final SearchState state = searchViewModel.getState();
            SearchController.goBack(state.getUsername(), state.getPassword());
        }
    }

    private void performSearch() {
        final SearchState currentState = searchViewModel.getState();
        String query = currentState.getSearchQuery();

        if (query == null || query.isEmpty()) {
            errorLabel.setText("Please enter a company name");
            return;
        }

        errorLabel.setText("");
        statusLabel.setText("Searching for: " + query);
        SearchController.executeSearch(query);
    }

    private void performLoadAll() {
        errorLabel.setText("");
        statusLabel.setText("Loading all results");
        SearchController.executeLoadAll();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final SearchState state = (SearchState) evt.getNewValue();

            // Update error signature
            errorLabel.setText(state.getSearchError());
            searchField.setText(state.getSearchQuery());

            updateTable(state.getSearchResults());
            int resultCount = state.getSearchResults().size();

            statusLabel.setText(String.format("Found %d stock%s",
                        resultCount, resultCount == 1 ? "" : "s"));
            }
        }

    private void updateTable(Map<String, Stock_Search> stocks) {
        tableModel.setRowCount(0);

        for (String stock : stocks.keySet()) {
            Object[] row = {
                    stocks.get(stock).getSymbol(),
                    stocks.get(stock).getCompany(),
                    formatPrice(stocks.get(stock).getPrice()),
                    stocks.get(stock).getCountry()
            };
            tableModel.addRow(row);
        }
    }

    private String formatPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return "N/A";
        }
        return String.format("%.2f", price);
    }

    public String getViewName() {
        return viewName;
    }

    public void setSearchController(SearchController SearchController) {
        this.SearchController = SearchController;
    }

    public void setPriceController(PriceController priceController) {
        this.priceController = priceController;
    }
}



