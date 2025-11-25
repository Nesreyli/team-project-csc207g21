package View;

import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.portfolio.PortfolioState;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import InterfaceAdapter.usersession.UserSessionViewModel;
import UI.GridBagHelper;
import UI.RoundedPanel;
import UI.UIColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class PortfolioPanel extends JPanel {
    private final String viewName = "portfolio";
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioController portfolioController;
    private final JTable holdingsTable;
    private final JLabel buyingPower;
    private final JLabel cash;
    private final JLabel dailyChange;
    private JPanel holdings;
    private final javax.swing.table.DefaultTableModel tableModel;

    public PortfolioPanel(PortfolioController portfolioController, PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioController = portfolioController;

        cash = new JLabel(portfolioViewModel.getState().cashBalance().toString());
        buyingPower = new JLabel(portfolioViewModel.getState().totalPortfolioValue().toString());
        dailyChange = new JLabel(portfolioViewModel.getState().performancePercentage().toString());
        // holdings = new JPanel(portfolioViewModel.getState().holdings());

        GridBagLayout gridBagLayout = new GridBagLayout();

        RoundedPanel balancesPanel = new RoundedPanel();
        balancesPanel.setLayout(gridBagLayout);

        final JLabel balancesInfo = new JLabel("Balances");
        balancesInfo.setFont(new Font(balancesInfo.getFont().deriveFont(Font.BOLD).getFontName(), Font.PLAIN, 14));

        GridBagHelper.addObjects(balancesInfo, balancesPanel, gridBagLayout, new GridBagConstraints(), 0, 0, 1, 1, GridBagConstraints.WEST);

        final JLabel buyingPowerInfo = new JLabel("Buying Power");

        GridBagHelper.addObjects(buyingPowerInfo, balancesPanel, gridBagLayout, new GridBagConstraints(), 0, 1, 1, 1, GridBagConstraints.WEST);

        GridBagHelper.addObjects(buyingPower, balancesPanel, gridBagLayout, new GridBagConstraints(), 0, 2, 1, 1, GridBagConstraints.WEST);

        final JLabel cashInfo = new JLabel("Cash");


        GridBagHelper.addObjects(cashInfo, balancesPanel, gridBagLayout, new GridBagConstraints(), 1, 1, 1, 1, GridBagConstraints.WEST);
        GridBagHelper.addObjects(cash, balancesPanel, gridBagLayout, new GridBagConstraints(), 1, 2, 1, 1, GridBagConstraints.WEST);

        final JLabel dailyChangeInfo = new JLabel("Daily Change");

        GridBagHelper.addObjects(dailyChangeInfo, balancesPanel, gridBagLayout, new GridBagConstraints(), 2, 1, 1, 1, GridBagConstraints.WEST);
        GridBagHelper.addObjects(dailyChange, balancesPanel, gridBagLayout, new GridBagConstraints(), 2, 2, 1, 1, GridBagConstraints.WEST);
        balancesPanel.setBackground(UIColor.PANEL);

        add(balancesPanel);

        tableModel = new javax.swing.table.DefaultTableModel(new Object[]{"Symbol", "Quantity"}, 0);
        holdingsTable = new JTable(tableModel);
        add(new JScrollPane(holdingsTable), BorderLayout.CENTER);


        portfolioViewModel.addPropertyChangeListener(evt -> {
            var state = portfolioViewModel.getState();
            // Update labels, tables, charts etc.
            cash.setText(state.cashBalance().toString());
            buyingPower.setText(state.totalPortfolioValue().toString());
            dailyChange.setText(state.performancePercentage().toString());
            updateHoldingsTable(state.holdings());
        });

    }

    private void updateHoldingsTable(Map<String, Object> holdings) {
        tableModel.setRowCount(0); // clear existing rows
        for (Map.Entry<String, Object> entry : holdings.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
}
