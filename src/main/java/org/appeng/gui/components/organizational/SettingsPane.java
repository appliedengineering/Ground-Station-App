package org.appeng.gui.components.organizational;

import org.appeng.backend.CommunicationsManager;
import org.appeng.backend.DataManager;
import org.appeng.backend.LogUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPane extends TabbedPane {

    private static final String TAG = "SettingsPane";


    private SettingsSection boatDataNetworkSettings;
    private SettingsSection timestampNetworkSettings;


    public static String[] boatDataNetworkLabels = {"Boat IP Address: ", "Boat Port: "};
    public static String[] boatDataNetworkDataIds = {"boatDataIpAddress", "boatDataPort"};

    public static String[] timeStampNetworkLabels = {"Timestamp Server IP Address: ", "Timestamp Server Port: "};
    public static String[] timeStampNetworkDataIds = {"timestampIpAddress", "timestampPort"};

    private JPanel editControls;
    private JButton restartNetworkBtn;

    private CommunicationsManager communicationsManager;

    public SettingsPane(String title, Icon icon, DataManager dataManager, CommunicationsManager communicationsManager) {
        super(title, icon, dataManager);
        this.communicationsManager = communicationsManager;
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


        editControls = new JPanel();

        restartNetworkBtn = new JButton("Restart Network");

        restartNetworkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // restart network
                try {
                    communicationsManager.restartNetworking();
                } catch (InterruptedException ex) {
                    LogUtil.addError(TAG, "Failed to restart network", dataManager);
                }
            }
        });

        editControls.add(restartNetworkBtn);

        this.add(editControls, BorderLayout.SOUTH);


    }

}
