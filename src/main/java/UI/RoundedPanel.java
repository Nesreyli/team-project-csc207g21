package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private Color borderColor;
    private int cornerRadius;
    private int borderThickness;
    private int padding;

    public RoundedPanel() {
        super();
        this.cornerRadius = 10;
        this.backgroundColor = UIColor.PANEL;
        this.borderColor = UIColor.OUTLINE;
        this.borderThickness = 1;
        this.padding = 10;
        setOpaque(false);
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(backgroundColor != null ? backgroundColor : getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        if (borderColor != null && borderThickness > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        }
    }

    public void setCornerRadius(int radius) { this.cornerRadius = radius; repaint(); }
    public void setBackgroundColor(Color color) { this.backgroundColor = color; repaint(); }
    public void setBorderColor(Color color) { this.borderColor = color; repaint(); }
    public void setBorderThickness(int thickness) { this.borderThickness = thickness; repaint(); }
    public void setPadding(int padding) {
        this.padding = padding;
        setBorder(new EmptyBorder(padding, padding, padding, padding));
        repaint();
    }
}
