package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserPurchasesView extends JFrame {
    private final String viewName = "User Purchases";
    private final String username;
    
    // Dummy stock data
    private static final String[][] STOCK_POOL = {
        {"AAPL", "Apple Inc.", "150.25"},
        {"GOOGL", "Alphabet Inc.", "125.50"},
        {"MSFT", "Microsoft Corporation", "350.75"},
        {"TSLA", "Tesla Inc.", "245.30"},
        {"AMZN", "Amazon.com Inc.", "135.80"},
        {"META", "Meta Platforms Inc.", "280.45"},
        {"NVDA", "NVIDIA Corporation", "420.15"},
        {"JPM", "JPMorgan Chase & Co.", "145.60"},
        {"V", "Visa Inc.", "220.90"},
        {"WMT", "Walmart Inc.", "155.40"}
    };
    
    public UserPurchasesView(String username) {
        this.username = username;
        
        this.setTitle(username + " - Purchase History");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));
        headerPanel.setBackground(new Color(240, 248, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        usernameLabel.setForeground(new Color(43, 45, 48));
        
        JLabel subtitleLabel = new JLabel("Purchase History");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        
        JPanel leftHeader = new JPanel();
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.setBackground(new Color(240, 248, 255));
        leftHeader.add(usernameLabel);
        leftHeader.add(Box.createVerticalStrut(5));
        leftHeader.add(subtitleLabel);
        
        headerPanel.add(leftHeader, BorderLayout.WEST);
        
        // Generate dummy purchases
        List<Purchase> purchases = generateDummyPurchases();
        
        // Table for purchases
        String[] columnNames = {"Date", "Stock", "Company", "Shares", "Price/Share", "Total Value"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        for (Purchase purchase : purchases) {
            Object[] row = {
                dateFormat.format(purchase.date),
                purchase.symbol,
                purchase.company,
                purchase.shares,
                String.format("$%.2f", purchase.pricePerShare),
                String.format("$%.2f", purchase.totalValue)
            };
            tableModel.addRow(row);
        }
        
        JTable purchasesTable = new JTable(tableModel);
        purchasesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        purchasesTable.setRowHeight(28);
        purchasesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        purchasesTable.setShowGrid(false); // Remove grid lines
        purchasesTable.setIntercellSpacing(new Dimension(0, 2)); // Add spacing between rows
        purchasesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        purchasesTable.getTableHeader().setBackground(new Color(60, 63, 66));
        purchasesTable.getTableHeader().setForeground(Color.WHITE);
        
        // Custom renderer for stock symbols (make them bold and slightly larger)
        DefaultTableCellRenderer symbolRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 1) { // Stock column
                    c.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    c.setForeground(new Color(0, 100, 0));
                }
                return c;
            }
        };
        purchasesTable.getColumnModel().getColumn(1).setCellRenderer(symbolRenderer);
        
        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        purchasesTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        // Right align price columns with larger font
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 4 || column == 5) { // Price columns
                    label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    if (column == 5) { // Total Value column
                        label.setForeground(new Color(0, 120, 0));
                    }
                }
                label.setHorizontalAlignment(JLabel.RIGHT);
                return label;
            }
        };
        purchasesTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        purchasesTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        
        JScrollPane scrollPane = new JScrollPane(purchasesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1)); // Lighter border
        
        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(new Color(250, 250, 250));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1), // Lighter border
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        BigDecimal totalValue = purchases.stream()
                .map(p -> p.totalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalShares = purchases.stream()
                .mapToInt(p -> p.shares)
                .sum();
        
        JPanel purchasePanel = new JPanel();
        purchasePanel.setLayout(new BoxLayout(purchasePanel, BoxLayout.Y_AXIS));
        purchasePanel.setBackground(new Color(250, 250, 250));
        JLabel totalPurchasesLabel = new JLabel("Total Purchases");
        totalPurchasesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalPurchasesLabel.setForeground(Color.GRAY);
        JLabel totalPurchasesValue = new JLabel(String.valueOf(purchases.size()));
        totalPurchasesValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPurchasesValue.setForeground(new Color(43, 45, 48));
        purchasePanel.add(totalPurchasesLabel);
        purchasePanel.add(Box.createVerticalStrut(5));
        purchasePanel.add(totalPurchasesValue);
        
        JPanel sharesPanel = new JPanel();
        sharesPanel.setLayout(new BoxLayout(sharesPanel, BoxLayout.Y_AXIS));
        sharesPanel.setBackground(new Color(250, 250, 250));
        JLabel totalSharesLabel = new JLabel("Total Shares");
        totalSharesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalSharesLabel.setForeground(Color.GRAY);
        JLabel totalSharesValue = new JLabel(String.format("%,d", totalShares));
        totalSharesValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalSharesValue.setForeground(new Color(43, 45, 48));
        sharesPanel.add(totalSharesLabel);
        sharesPanel.add(Box.createVerticalStrut(5));
        sharesPanel.add(totalSharesValue);
        
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));
        valuePanel.setBackground(new Color(250, 250, 250));
        JLabel totalValueLabel = new JLabel("Total Value");
        totalValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalValueLabel.setForeground(Color.GRAY);
        JLabel totalValueValue = new JLabel("$" + String.format("%,.2f", totalValue));
        totalValueValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalValueValue.setForeground(new Color(0, 150, 0));
        valuePanel.add(totalValueLabel);
        valuePanel.add(Box.createVerticalStrut(5));
        valuePanel.add(totalValueValue);
        
        summaryPanel.add(purchasePanel);
        summaryPanel.add(sharesPanel);
        summaryPanel.add(valuePanel);
        
        // Button Panel with more space above
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0)); // More space above button
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(120, 35));
        closeButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(closeButton);
        
        // Use a wrapper to add both summary and button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(summaryPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }
    
    private List<Purchase> generateDummyPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        Random random = new Random(username.hashCode()); // Deterministic based on username
        
        Calendar cal = Calendar.getInstance();
        int numPurchases = 5 + random.nextInt(8); // 5-12 purchases
        
        for (int i = 0; i < numPurchases; i++) {
            String[] stock = STOCK_POOL[random.nextInt(STOCK_POOL.length)];
            int shares = 10 + random.nextInt(90); // 10-100 shares
            double price = Double.parseDouble(stock[2]) * (0.8 + random.nextDouble() * 0.4); // ±20% variation
            BigDecimal pricePerShare = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalValue = pricePerShare.multiply(BigDecimal.valueOf(shares));
            
            // Random date in the past 6 months
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -random.nextInt(180));
            Date purchaseDate = cal.getTime();
            
            purchases.add(new Purchase(
                purchaseDate,
                stock[0],
                stock[1],
                shares,
                pricePerShare,
                totalValue
            ));
        }
        
        // Sort by date (newest first)
        purchases.sort((a, b) -> b.date.compareTo(a.date));
        
        return purchases;
    }
    
    public String getViewName() {
        return viewName;
    }
    
    private static class Purchase {
        Date date;
        String symbol;
        String company;
        int shares;
        BigDecimal pricePerShare;
        BigDecimal totalValue;
        
        Purchase(Date date, String symbol, String company, int shares, 
                BigDecimal pricePerShare, BigDecimal totalValue) {
            this.date = date;
            this.symbol = symbol;
            this.company = company;
            this.shares = shares;
            this.pricePerShare = pricePerShare;
            this.totalValue = totalValue;
        }
    }
}

