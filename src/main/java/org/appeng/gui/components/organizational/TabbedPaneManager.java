package org.appeng.gui.components.organizational;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TabbedPaneManager extends JTabbedPane{

    private List<TabbedPane> contentPanes = new ArrayList<>();

    public void registerPane(TabbedPane pane) {
        contentPanes.add(pane);
        this.addTab(pane.getTitle(), pane.getIcon(), pane,
                pane.getTitle());
    }

}
