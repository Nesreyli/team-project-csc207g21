package UI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UIFont {

    private static Font REGULAR_FONT;
    private static Font BOLD_FONT;

    public static void setGlobalFont() {
        try {
            InputStream regularStream = UIFont.class.getResourceAsStream("/fonts/pt-root-ui_regular.ttf");
            REGULAR_FONT = Font.createFont(Font.TRUETYPE_FONT, regularStream).deriveFont(12f);

            InputStream boldStream = UIFont.class.getResourceAsStream("/fonts/pt-root-ui_medium.ttf");
            BOLD_FONT = Font.createFont(Font.TRUETYPE_FONT, boldStream).deriveFont(12f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(REGULAR_FONT);
            ge.registerFont(BOLD_FONT);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            REGULAR_FONT = new Font("SansSerif", Font.PLAIN, 12);
            BOLD_FONT = new Font("SansSerif", Font.BOLD, 12);
        }

        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                String fontKey = key.toString().toLowerCase();
                if (fontKey.contains("bold")) {
                    UIManager.put(key, BOLD_FONT);
                } else {
                    UIManager.put(key, REGULAR_FONT);
                }
            }
        }
    }
}