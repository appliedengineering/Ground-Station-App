package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.UpdateCallback;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import javax.swing.text.StringContent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DebugPane extends TabbedPane implements UpdateCallback {

    private JTextArea debugText;
    private JScrollPane scrollPane;
    private JToggleButton autoScroll;
    private JButton clearLog;

    private boolean shouldScroll = false;

    public DebugPane(String title, Icon icon, DataManager dataManager) {
        super(title, icon, dataManager);
        init();
    }

    private void init() {
        Color bg = new Color(34, 34, 34);

        dataManager.registerConfigCallback(this);
        debugText = new JTextArea();
        scrollPane = new JScrollPane(debugText);

        this.setBackground(bg);
        debugText.setBackground(bg);
        scrollPane.setBackground(bg);


        scrollPane.setBorder(null);
        debugText.setToolTipText(null);
        debugText.setEditable(false);
        debugText.setText("2022 Applied Engineering\n[START] DEBUG LOG\n");

        // styling
        debugText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        debugText.setForeground(Color.GREEN);

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);



        JPanel panel = new JPanel();

        autoScroll = new JToggleButton("Auto Scroll? [OFF]");
        autoScroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shouldScroll = !shouldScroll;
                if(shouldScroll) {
                    autoScroll.setText("Auto Scroll? [ON]");
                } else {
                    autoScroll.setText("Auto Scroll? [OFF]");
                }
            }
        });
        clearLog = new JButton("Clear Log");

        clearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataManager.debugText = "";
                dataManager.pushConfigUpdate();
            }
        });

        panel.add(autoScroll);
        panel.add(clearLog);

        this.add(panel, BorderLayout.NORTH);
    }

    @Override
    public void onDataUpdate() {
        if(debugText != null) {
            debugText.setText("2022 Applied Engineering\n[START] DEBUG LOG\n");
            debugText.append(dataManager.debugText);
            if(shouldScroll) {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        }
    }
}
