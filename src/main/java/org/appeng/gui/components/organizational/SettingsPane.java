package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPane extends TabbedPane {


    private NetworkingSettingsSection boatDataNetworkSettings;


    public SettingsPane(String title, Icon icon, DataManager dataManager) {
        super(title, icon, dataManager);
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        boatDataNetworkSettings = new NetworkingSettingsSection(dataManager);

        this.add(boatDataNetworkSettings, BorderLayout.NORTH);

    }

}
