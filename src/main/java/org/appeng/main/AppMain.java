package org.appeng.main;

import org.appeng.backend.Backend;
import org.appeng.gui.AppWindow;
import org.appeng.gui.components.organizational.*;
import org.appeng.lookandfeel.LookAndFeelManager;
import org.appeng.util.resources.images.ImageLoaderUtil;

import java.awt.*;

public class AppMain {

    private AppWindow window;
    private TabbedPaneManager tabbedPaneManager;
    private StatusBar statusBar;

    private Backend backend;

    private DataPane dataPane;

    public void start(){
        initWindow();
    }

    public void initWindow() {

        backend = new Backend();
        backend.init();

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
        tabbedPaneManager.registerPane(new TabbedPane("Realtime Boat Dashboard", null, backend.getDataManager()));

        tabbedPaneManager.registerPane(new SettingsPane("Settings", null, backend.getDataManager(), backend.getCommunicationsManager()));

        tabbedPaneManager.registerPane(new DebugPane("Debug </>", null, backend.getDataManager()));

        window.add(tabbedPaneManager, BorderLayout.CENTER);

        // add data pane
        dataPane = new DataPane(backend.getDataManager());

        window.add(dataPane, BorderLayout.WEST);

        statusBar = new StatusBar(backend.getDataManager());

        window.add(statusBar, BorderLayout.SOUTH);

        window.setVisible(true);

        backend.start();
    }


}
