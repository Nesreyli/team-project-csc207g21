package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import interface_adapter.homebutton.HomeController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.stock_price.PriceController;

public class PortfolioView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "portfolio";
    private final PortfolioViewModel portfolioViewModel;
    private final JButton username;
    private final JLabel value;
    private final JLabel cash;
    private final JLabel performance;
    private JPanel holdings;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private final JTable resultsTable;
    private PriceController priceController;


    private HomeController homeController;

    public PortfolioView(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioViewModel.addPropertyChangeListener(this);

        final String[] columnNames = {"Symbol", "Price (USD)", "Performance", "Valuations"};
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

        final JLabel user = new JLabel("Portfolio: ");
        user.setFont(new Font("Arial", Font.BOLD, 17));
        username = new JButton();
        value = new JLabel();
        cash = new JLabel();
        performance = new JLabel();
        holdings = new JPanel();

        username.addActionListener(this::actionPerformed);

        final JPanel portUser = new JPanel();
        final JPanel perf = new JPanel();
        final JPanel userCash = new JPanel();
        final JPanel val = new JPanel();

        perf.add(new JLabel("Total return since inception: "));
        perf.add(performance);
        perf.setBackground(new Color(211, 211, 211));
        performance.setFont(new Font("Arial", Font.BOLD, 17));

        userCash.add(new JLabel("Buying Power: "));
        userCash.add(cash);
        userCash.setBackground(new Color(211, 211, 211));
        cash.setFont(new Font("Arial", Font.BOLD, 15));

        val.add(new JLabel("Total Valuation: "));
        val.add(value);
        val.setBackground(new Color(211, 211, 211));
        value.setFont(new Font("Arial", Font.BOLD, 15));

        portUser.add(username);
        portUser.add(user);
        portUser.setBackground(new Color(211, 211, 211));

        holdings.setBackground(Color.LIGHT_GRAY);
        holdings.add(new JLabel("Positions:"));
        holdings.setLayout(new BoxLayout(holdings, BoxLayout.Y_AXIS));
        final JScrollPane positions = new JScrollPane(holdings);
        positions.setBackground(Color.LIGHT_GRAY);

        final JPanel box = new JPanel();
        box.setBackground(Color.LIGHT_GRAY.brighter());

        portUser.setLayout(new FlowLayout(FlowLayout.LEFT));

        box.add(portUser);
        box.add(perf);
        box.add(val);
        box.add(userCash);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        this.setLayout(new GridLayout(1, 2));
        this.add(box);
        this.add(positions);
        this.add(scrollPane);
        this.setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Handles actions triggered by the Home button.
     * When the Home button is clicked, this method retrieves the current portfolio
     * state (username and password) from the view model and calls the home controller
     * to navigate back to the home view.
     * @param evt the  ActionEvent triggered by user interaction
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(username)) {
            final PortfolioState currentState = portfolioViewModel.getState();
            homeController.execute(
                    currentState.getUsername(),
                    currentState.getPassword()
            );
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final PortfolioState state = (PortfolioState) evt.getNewValue();
            username.setText(state.getUsername());
            value.setText(state.getValue());
            cash.setText(state.getCash());
            final String tempPerf = state.getPerformance();
            performance.setText(tempPerf);
            if (Integer.parseInt(tempPerf.substring(0, tempPerf.length() - 1).replace(".", "")) >= 0) {
                performance.setForeground(Color.GREEN.darker());
            }
            else {
                performance.setForeground(Color.RED.darker());
            }

            final Map<String, Object> positions = state.getHoldings();
            // without remove all this thing just stacks again again again has memory of previous inputs.
            holdings.removeAll();
            for (String symbol: positions.keySet()) {
                final JPanel owning = new JPanel();
                owning.add(new JLabel(symbol));
                owning.add(new JLabel(positions.get(symbol).toString()));
                owning.add(new JLabel(state.getPrices().get(symbol).toString()));
                owning.add(new JLabel(state.getStockPerformance().get(symbol).toString()));
                owning.setBackground(Color.LIGHT_GRAY);
                holdings.add(owning);
            }
        }
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

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homecontroller) {
        this.homeController = homecontroller;
    }

    public void setPriceController(PriceController priceController) {
        this.priceController = priceController;
    }
}
