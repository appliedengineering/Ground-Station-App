package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.UpdateCallback;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JPanel {

    private String title = "frame";
    private Icon icon = null;
    protected DataManager dataManager;

    public TabbedPane() {
    }

    public TabbedPane(String title, Icon icon, DataManager dataManager) {
        this.title = title;
        this.icon = icon;
        this.dataManager = dataManager;

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
