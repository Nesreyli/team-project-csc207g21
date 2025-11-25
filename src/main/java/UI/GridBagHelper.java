package UI;// Source - https://stackoverflow.com/a
// Posted by Antonio Aguilar, modified by community. See post 'Timeline' for change history
// Retrieved 2025-11-24, License - CC BY-SA 3.0

import java.awt.*;

public class GridBagHelper {

public static void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridWidth, int gridHeight, int anchor){

    gbc.gridx = gridx;
    gbc.gridy = gridy;

    gbc.gridwidth = gridWidth;
    gbc.gridheight = gridHeight;

    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    gbc.anchor = anchor;

    gbc.insets = new Insets(5,5,5,5);

    layout.setConstraints(component, gbc);
    container.add(component);
}
}
