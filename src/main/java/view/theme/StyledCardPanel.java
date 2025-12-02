package view.theme;

import javax.swing.*;
import java.awt.*;

/**
 * The Styled Card Panel for consistent card-like UI components.
 */
public class StyledCardPanel extends JPanel {

    public StyledCardPanel() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
    }
}
