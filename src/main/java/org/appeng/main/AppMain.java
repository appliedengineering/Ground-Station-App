package org.appeng.main;

import org.appeng.gui.AppWindow;
import org.appeng.gui.components.organizational.DataPane;
import org.appeng.gui.components.organizational.StatusBar;
import org.appeng.gui.components.organizational.TabbedPane;
import org.appeng.gui.components.organizational.TabbedPaneManager;
import org.appeng.lookandfeel.LookAndFeelManager;
import org.appeng.util.resources.images.ImageLoaderUtil;

import java.awt.*;

public class AppMain {

    private AppWindow window;
    private TabbedPaneManager tabbedPaneManager;
    private StatusBar statusBar;

    private DataPane dataPane;

    public void start(){
        initWindow();
    }

    public void initWindow() {

        LookAndFeelManager.setupDarkMode();

        window = new AppWindow();
        window.setTitle("Applied Engineering Ground Station App");
        window.setIconImage(ImageLoaderUtil.getLogo().getImage());
        window.setExpanded();

        // setup
        // layout manager
        window.setLayout(new BorderLayout());

        // add tabbed panes
        tabbedPaneManager = new TabbedPaneManager();

        // create new panes
        tabbedPaneManager.registerPane(new TabbedPane("panel 1", null));

        tabbedPaneManager.registerPane(new TabbedPane("panel 2", null));

        tabbedPaneManager.registerPane(new TabbedPane("panel 3", null));

        window.add(tabbedPaneManager, BorderLayout.CENTER);

        // add data pane
        dataPane = new DataPane();

        window.add(dataPane, BorderLayout.WEST);

        statusBar = new StatusBar();

        window.add(statusBar, BorderLayout.SOUTH);

        window.setVisible(true);
    }


}
