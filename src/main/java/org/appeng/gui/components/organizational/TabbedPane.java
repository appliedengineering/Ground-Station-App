package org.appeng.gui.components.organizational;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JPanel {

    private String title = "frame";
    private Icon icon = null;

    public TabbedPane() {
    }

    public TabbedPane(String title, Icon icon) {
        this.title = title;
        this.icon = icon;

        init();
    }

    private void init() {
        Color bg = new Color(34, 34, 34);
        this.setBackground(bg);
    }

    // GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
