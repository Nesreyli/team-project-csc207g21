package view;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final JTextField searchField = new JTextField(25);
    private final JButton searchButton;
    private final JButton loadallButton;
    private final JLabel errorLabel;
    private final JTable resultsTable;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private final JLabel statusLabel = new JLabel("Ready to search");
    private final JButton previous;


    public SearchView(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(15, 15));
        this.setBackground(new Color(240, 242, 245));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Panel with Back Button and Title
        final JPanel topPanel = createTopPanel();

        // Search Panel with styled input
        final JPanel searchPanel = createSearchPanel();

        // Initialize table
        final String[] columnNames = {"Symbol", "Company", "Price (USD)", "Country"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        setupTable();

        scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218), 1));
        scrollPane.setBackground(Color.WHITE);

        // Results panel wrapper
        final JPanel resultsPanel = new JPanel(new BorderLayout(10, 10));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Status panel at bottom
        final JPanel statusPanel = createStatusPanel();

        // Assemble layout
        final JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 242, 245));
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(resultsPanel, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);

        // Initialize buttons
        searchButton = createStyledButton("Search", new Color(0, 123, 255), Color.WHITE);
        searchButton.addActionListener(this);

        loadallButton = createStyledButton("Load All", new Color(108, 117, 125), Color.WHITE);
        loadallButton.addActionListener(this);

        previous = createBackButton();
        previous.addActionListener(this);

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(220, 53, 69));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setVisible(false);

        // Add components to search panel
        addComponentsToSearchPanel(searchPanel);

        // Add document listener for real-time search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SearchState currentState = searchViewModel.getState();
                currentState.setSearchQuery(searchField.getText());
                searchViewModel.setState(currentState);
                errorLabel.setVisible(false);
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

    private JPanel createTopPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 242, 245));

        final JLabel title = new JLabel("Stock Search");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(51, 51, 51));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel subtitle = new JLabel("Search for stocks by symbol or company name");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(120, 120, 120));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);

        return panel;
    }

    private JPanel createSearchPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        return panel;
    }

    private void addComponentsToSearchPanel(JPanel searchPanel) {
        // Main horizontal layout panel
        final JPanel mainRow = new JPanel(new GridBagLayout());
        mainRow.setBackground(Color.WHITE);
        mainRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Back button on the left
        gbc.gridx = 0;
        gbc.weightx = 0;
        mainRow.add(previous, gbc);

        // Search input in the center
        final JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        final JLabel iconLabel = new JLabel("🔍");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        searchField.setBorder(null);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBackground(Color.WHITE);

        // Add focus listeners
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        inputPanel.add(iconLabel, BorderLayout.WEST);
        inputPanel.add(searchField, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        mainRow.add(inputPanel, gbc);

        // Search button
        gbc.gridx = 2;
        gbc.weightx = 0;
        mainRow.add(searchButton, gbc);

        // Load All button
        gbc.gridx = 3;
        gbc.weightx = 0;
        mainRow.add(loadallButton, gbc);

        // Error label below
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchPanel.add(mainRow);
        searchPanel.add(Box.createVerticalStrut(10));
        searchPanel.add(errorLabel);
    }

    private JButton createBackButton() {
        final JButton button = new JButton("← Back");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(0, 123, 255));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 1),
                new EmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(248, 249, 250));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    private JButton createStyledButton(String text, Color background, Color foreground) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(background.darker());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(background);
            }
        });

        return button;
    }

    private void setupTable() {
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultsTable.setRowHeight(32);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsTable.setShowGrid(true);
        resultsTable.setGridColor(new Color(240, 240, 240));
        resultsTable.setSelectionBackground(new Color(232, 240, 254));
        resultsTable.setSelectionForeground(new Color(51, 51, 51));

        // Header styling
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultsTable.getTableHeader().setBackground(new Color(248, 249, 250));
        resultsTable.getTableHeader().setForeground(new Color(51, 51, 51));
        resultsTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
                new Color(206, 212, 218)));

        // Custom renderer for Symbol column (clickable link style)
        resultsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                setForeground(new Color(0, 123, 255));
                setFont(getFont().deriveFont(Font.BOLD));
                setText("<html><u>" + value + "</u></html>");
                return c;
            }
        });

        // Click handler for table
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int row = resultsTable.rowAtPoint(e.getPoint());
                final int column = resultsTable.columnAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    final String symbol = (String) resultsTable.getValueAt(row, 0);
                    priceController.executePrice(symbol);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resultsTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resultsTable.setCursor(Cursor.getDefaultCursor());
            }
        });

        // Enable sorting
        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);
    }

    private JPanel createStatusPanel() {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(240, 242, 245));
        panel.setBorder(new EmptyBorder(10,0,0,0));

        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(120, 120, 120));
        panel.add(statusLabel);
        return panel;
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
            errorLabel.setText("Please enter a Stock Symbol or Company Name");
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

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final SearchState state = (SearchState) evt.getNewValue();

            // Update error message
            if (state.getSearchError() != null && !state.getSearchError().isEmpty()) {
                errorLabel.setText(state.getSearchError());
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
            }

            searchField.setText(state.getSearchQuery());
            updateTable(state.getSearchResults());

            final int resultCount = state.getSearchResults().size();
            if (resultCount > 0) {
                statusLabel.setText(String.format("Found %d stock%s",
                        resultCount, resultCount == 1 ? "" : "s"));
            } else if (!errorLabel.isVisible()) {
                statusLabel.setText("No results found");
            }
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
