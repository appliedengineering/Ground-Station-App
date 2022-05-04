package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.UpdateCallback;
import org.appeng.backend.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsContainer extends JPanel implements UpdateCallback {
    private DataManager dataManager;
    private String dataId, dataLabel;

    private JLabel label, value;

    public StatsContainer(String dataId, String dataLabel, DataManager dataManager) {
        this.dataManager = dataManager;
        this.dataId = dataId;
        this.dataLabel = dataLabel;
        init();
    }

    private void init() {
        Color bg = new Color(34, 34, 34);

        setBackground(bg);

        this.setLayout(new BorderLayout());
        int p = 20;
        this.setBorder(new EmptyBorder(p, p, p, p));

        label = new JLabel(Util.htmlTagFormat(dataLabel, "h2"));
        value = new JLabel("0.00");

        this.add(label, BorderLayout.NORTH);
        this.add(value, BorderLayout.CENTER);

        dataManager.registerCallback(this);
    }

    @Override
    public void onDataUpdate() {

    }
}
