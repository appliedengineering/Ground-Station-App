package org.appeng.gui.components.organizational;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DataPane extends JPanel {

    public DataPane() {
        init();
    }

    private void init() {
        int padding = 20;
        this.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY),
                new EmptyBorder(padding/2, padding*4, 0, padding*4)
        ));
        this.add(new JLabel("Dashboard Panel"));
    }
}
