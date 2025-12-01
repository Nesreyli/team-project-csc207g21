package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import entity.Stock_Search;
import interface_adapter.Stock_Search.SearchController;
import interface_adapter.Stock_Search.SearchState;
import interface_adapter.Stock_Search.SearchViewModel;
import interface_adapter.stock_price.PriceController;

/**
 * The View for Stock Search functionality with clickable stock symbols..
 */

public class SearchView extends JPanel implements  ActionListener, PropertyChangeListener {
    private final String viewName = "Stock Search";
    private final SearchViewModel searchViewModel;
    private SearchController searchController;
    private PriceController priceController;

    // UI Stuff
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

        final String[] columnNames = {"Symbol", "Company", "Price (CAD)", "Country"};
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
                final Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
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
            public void mouseClicked(MouseEvent e) {
                final int row = resultsTable.rowAtPoint(e.getPoint());
                final int column = resultsTable.columnAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    final String symbol = (String) resultsTable.getValueAt(row, 0);
                    // new Stock Price tab
                    priceController.executePrice(symbol);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                final int column = resultsTable.columnAtPoint(e.getPoint());
                if (column >= 0) {
                    resultsTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resultsTable.setCursor(Cursor.getDefaultCursor());
            }
        });

        // Sorting Capability
        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);

        scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Layout
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(Color.LIGHT_GRAY);

        final JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.LIGHT_GRAY);
        final JLabel title = new JLabel(SearchViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        final JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.LIGHT_GRAY);
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        final JLabel searchLabel = new JLabel(SearchViewModel.SEARCH_FIELD_LABEL + ":");
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
        final JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Status Panel
        final JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        searchField.addActionListener(event -> performSearch());
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(searchButton)) {
            performSearch();
        }
        else if (evt.getSource().equals(loadallButton)) {
            performLoadAll();
        }
        else if (evt.getSource().equals(previous)) {
            final SearchState state = searchViewModel.getState();
            searchController.goBack(state.getUsername(), state.getPassword());
        }
    }

    private void performSearch() {
        final SearchState currentState = searchViewModel.getState();
        final String query = currentState.getSearchQuery();

        if (query == null || query.isEmpty()) {
            errorLabel.setText("Please enter a company name");
        }

        errorLabel.setText("");
        statusLabel.setText("Searching for: " + query);
        searchController.executeSearch(query);
    }

    private void performLoadAll() {
        errorLabel.setText("");
        statusLabel.setText("Loading all results");
        searchController.executeLoadAll();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final SearchState state = (SearchState) evt.getNewValue();

            // Update error signature
            errorLabel.setText(state.getSearchError());
            searchField.setText(state.getSearchQuery());

            updateTable(state.getSearchResults());
            final int resultCount = state.getSearchResults().size();

            statusLabel.setText(String.format("Found %d stock%s",
                        resultCount, resultCount == 1 ? "" : "s"));
        }
    }

    private void updateTable(Map<String, Stock_Search> stocks) {
        tableModel.setRowCount(0);

        for (String stock : stocks.keySet()) {
            final Object[] row = {
                    stocks.get(stock).getSymbol(),
                    stocks.get(stock).getCompany(),
                    formatPrice(stocks.get(stock).getPrice()),
                    stocks.get(stock).getCountry()};
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
        this.searchController = SearchController;
    }

    public void setPriceController(PriceController priceController) {
        this.priceController = priceController;
    }
}
