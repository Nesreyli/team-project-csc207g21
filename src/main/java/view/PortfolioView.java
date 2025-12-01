package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.*;

import interface_adapter.homebutton.HomeController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;

public class PortfolioView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "portfolio";
    private final PortfolioViewModel portfolioViewModel;
    private final JButton username;
    private final JLabel value;
    private final JLabel cash;
    private final JLabel performance;
    private JPanel holdings;

    private HomeController homeController;

    public PortfolioView(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioViewModel.addPropertyChangeListener(this);

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
                owning.setBackground(Color.LIGHT_GRAY);
                holdings.add(owning);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homecontroller) {
        this.homeController = homecontroller;
    }
}
