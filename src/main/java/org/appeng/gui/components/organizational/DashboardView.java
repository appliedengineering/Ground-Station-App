package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.UpdateCallback;
import org.appeng.backend.Util;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DashboardView extends TabbedPane implements UpdateCallback {

    JPanel dashContainer;
    JLabel helloAndTime;

    StatsContainer hoursDrivenStats, milesDrivenStats, avgSpeedStats;


    public DashboardView(String title, Icon icon, DataManager dataManager) {
        super(title, icon, dataManager);
        init();
    }

    public void init() {

        Color bg = new Color(34, 34, 34);

        this.setLayout(new BorderLayout());

        dashContainer = new JPanel(new GridBagLayout());

        dashContainer.setBackground(bg);
        this.setBackground(bg);


        helloAndTime = new JLabel();
        helloAndTime.setOpaque(false);

        // hello and time
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 3;
        c1.gridheight = 1;

        dashContainer.add(helloAndTime, c1);

        hoursDrivenStats = new StatsContainer("hoursDriven", "Hours Driven", dataManager);
        milesDrivenStats = new StatsContainer("milesDriven", "Miles Driven", dataManager);
        avgSpeedStats = new StatsContainer("avgSpeedStats", "Average Trip Speed", dataManager);


        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.gridwidth = 1;
        c2.gridheight = 1;

        dashContainer.add(hoursDrivenStats, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.gridheight = 1;

        dashContainer.add(milesDrivenStats, c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 2;
        c4.gridy = 1;
        c4.gridwidth = 1;
        c4.gridheight = 1;

        dashContainer.add(avgSpeedStats, c4);

        this.add(dashContainer, BorderLayout.NORTH);



        dataManager.registerCallback(this);

    }

    @Override
    public void onDataUpdate() {
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("hh : mm : ss a", Locale.ENGLISH));
        helloAndTime.setText(Util.htmlTagFormat("Hello, Applied Engineering!<br>Time is: " + formattedTime, "h1"));
    }
}
