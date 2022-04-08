package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPane extends TabbedPane {


    private SettingsSection boatDataNetworkSettings;
    private SettingsSection timestampNetworkSettings;


    public static String[] boatDataNetworkLabels = {"IP Address: ", "Port: "};
    public static String[] boatDataNetworkDataIds = {"boatDataIpAddress", "boatDataPort"};

    public static String[] timeStampNetworkLabels = {"Timestamp Server IP Address: ", "Timestamp Server Port: "};
    public static String[] timeStampNetworkDataIds = {"timestampIpAddress", "timestampPort"};

    public SettingsPane(String title, Icon icon, DataManager dataManager) {
        super(title, icon, dataManager);
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        boatDataNetworkSettings = new SettingsSection(dataManager);
        boatDataNetworkSettings.setFields(boatDataNetworkLabels, boatDataNetworkDataIds);
        boatDataNetworkSettings.init();

        timestampNetworkSettings = new SettingsSection(dataManager);
        timestampNetworkSettings.setFields(timeStampNetworkLabels, timeStampNetworkDataIds);
        timestampNetworkSettings.init();

        container.add(boatDataNetworkSettings);
        container.add(timestampNetworkSettings);

        this.add(container, BorderLayout.NORTH);


    }

}
