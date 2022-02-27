package org.appeng.gui.components.organizational;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBar extends JPanel {

    private JPanel left, right;

    public StatusBar() {
        init();
    }

    private void init() {
        left = new JPanel();
        right = new JPanel();

        Color bg = new Color(0, 108, 187);

        this.setBackground(bg);
        left.setBackground(bg);
        right.setBackground(bg);


        int p = 10;
        this.setBorder(new EmptyBorder(p/2, p, p/2, p));
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        left.add(new JLabel("Network Offline"));
        right.add(new JLabel("Applied Engineering 2022 v1.0.0"));

        this.setLayout(new BorderLayout());
        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
    }

}
