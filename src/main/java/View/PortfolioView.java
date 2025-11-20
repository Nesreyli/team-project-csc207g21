package View;

import InterfaceAdapter.logged_in.LoggedInState;
import InterfaceAdapter.portfolio.PortfolioController;
import InterfaceAdapter.portfolio.PortfolioState;
import InterfaceAdapter.portfolio.PortfolioViewModel;
import org.intellij.lang.annotations.Flow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class PortfolioView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "portfolio";
    private final PortfolioViewModel portfolioViewModel;
    private final JLabel username;
    private final JLabel value;
    private final JLabel cash;
    private final JLabel performance;
    private final JPanel holdings;

    public PortfolioView(PortfolioViewModel portfolioViewModel){
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioViewModel.addPropertyChangeListener(this);

        final JLabel user = new JLabel("Portfolio: ");
        username = new JLabel();
        value = new JLabel();
        cash = new JLabel();
        performance = new JLabel();
        holdings = new JPanel();

        JPanel portUser = new JPanel();
        JPanel perf = new JPanel();
        JPanel userCash = new JPanel();
        JPanel val = new JPanel();

        perf.add(new JLabel("Total return since inception: "));
        perf.add(performance);
        perf.setBackground(Color.LIGHT_GRAY);

        userCash.add(new JLabel("Buying Power: "));
        userCash.add(cash);
        userCash.setBackground(Color.LIGHT_GRAY);

        val.add(new JLabel("Total Valuation: "));
        val.add(value);
        val.setBackground(Color.LIGHT_GRAY);

        portUser.add(username);
        portUser.add(user);
        portUser.setBackground(Color.LIGHT_GRAY);

        holdings.setBackground(Color.LIGHT_GRAY);
        holdings.add(new JLabel("Positions:"));
        holdings.setLayout(new BoxLayout(holdings, BoxLayout.Y_AXIS));
        JScrollPane positions = new JScrollPane(holdings);
        positions.setBackground(Color.LIGHT_GRAY);

        JPanel box = new JPanel();
        box.setBackground(Color.LIGHT_GRAY.brighter());

        portUser.setLayout(new FlowLayout(FlowLayout.LEFT));

        box.add(portUser);
        box.add(perf);
        box.add(val);
        box.add(userCash);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        this.setLayout(new GridLayout(1,2));
        this.add(box);
        this.add(positions);
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void actionPerformed(ActionEvent evt) {
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final PortfolioState state = (PortfolioState) evt.getNewValue();
            username.setText(state.getUsername());
            value.setText(state.getValue());
            cash.setText(state.getCash());
            String tempPerf = state.getPerformance();
            performance.setText(tempPerf);
            if(Integer.parseInt(tempPerf.substring(0,tempPerf.length() - 1).replace(".","")) >= 0){
                performance.setForeground(Color.GREEN.darker());
            }
            else{
                performance.setForeground(Color.RED.darker());
            }
            Map<String, Object> positions = state.getHoldings();

            for(String symbol: positions.keySet()){
                JPanel owning = new JPanel();
                owning.add(new JLabel(symbol));
                owning.add(new JLabel(positions.get(symbol).toString()));
                owning.setBackground(Color.LIGHT_GRAY);
                holdings.add(owning);
            }
        }
//        else if (evt.getPropertyName().equals("password")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            if (state.getPasswordError() == null) {
//                JOptionPane.showMessageDialog(this, "password updated for " + state.getUsername());
//                passwordInputField.setText("");
//            }
//            else {
//                JOptionPane.showMessageDialog(this, state.getPasswordError());
//            }
//        }
    }
    public String getViewName() {
        return viewName;
    }
}
