package org.appeng.gui.components.misc;

import javax.swing.*;
import java.awt.*;

public class BoldLabel extends JLabel {
    public BoldLabel() {
        setBold();
    }

    private void setBold() {
        setFont(getFont().deriveFont(Font.BOLD));
    }

    public BoldLabel(String dashboard_panel) {
        super(dashboard_panel);
        setBold();
    }
}
