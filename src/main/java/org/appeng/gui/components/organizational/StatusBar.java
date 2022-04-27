package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.LogUtil;
import org.appeng.backend.UpdateCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusBar extends JPanel implements UpdateCallback {

    private static final String TAG = "StatusBar";

    private JPanel left, right;
    private DataManager dataManager;
    private JLabel networkStatus;

    public StatusBar(DataManager dataManager) {
        this.dataManager = dataManager;
        init();
    }

    private void init() {

        dataManager.registerConfigCallback(this);

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
        networkStatus = new JLabel();
        left.add(networkStatus);
        right.add(new JLabel("Applied Engineering 2022 v1.0.0"));

        this.setLayout(new BorderLayout());
        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
    }

    @Override
    public void onDataUpdate() {
        if(dataManager.boatDataStatus == -1) {
            networkStatus.setText("Network Offline");
        } else {
            networkStatus.setText("Network Online");
        }
    }
}
