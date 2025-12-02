package view.theme;

import javax.swing.*;
import java.awt.*;

import static view.theme.UITheme.FONT_NAME;

/**
 * A JButton subclass that applies a consistent theme to buttons.
 * Provides methods for creating primary and outline styled buttons.
 */
public class StyledButton extends JButton {
    /**
     * Creates a primary styled button with bold font and solid background.
     *
     * @param text the text to display on the button
     * @return a JButton styled as a primary button
     */
    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UITheme.PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Creates an outline styled button with border and themed colors.
     *
     * @param text the text to display on the button
     * @return a JButton styled as an outline button
     */
    public static JButton createOutlineButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btn.setForeground(UITheme.PRIMARY);
        btn.setBackground(UITheme.CARD_BG);
        btn.setBorder(BorderFactory.createLineBorder(UITheme.PRIMARY, 2));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
