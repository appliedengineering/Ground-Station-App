package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.backend.UpdateCallback;
import org.appeng.backend.Util;
import org.appeng.constants.SettingsConstants;
import org.appeng.data.BoatTripData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoatTripRecording extends TabbedPane implements UpdateCallback {

    private JPanel recordingBox;
    private JLabel recordingText;
    private JToggleButton recordButton;

    public BoatTripRecording(String title, Icon icon, DataManager dataManager) {
        super(title, icon, dataManager);
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());

        recordingBox = new JPanel();

        recordingText = new JLabel();
        recordButton = new JToggleButton();

        recordingBox.add(recordingText);
        recordingBox.add(recordButton);

        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoatTripData newTrip = new BoatTripData();
            }
        });


        this.add(recordingBox, BorderLayout.NORTH);

        dataManager.registerCallback(this);
    }

    @Override
    public void onDataUpdate() {
        if(dataManager.getSettingsManager().settings.get(SettingsConstants.currentRecordingId).equals("null")) {
            recordingText.setText(Util.htmlTagFormat("Start a recording: ", "h4"));
            recordButton.setSelected(false);
            recordButton.setText("Start Recording");
        } else {
            recordingText.setText(Util.htmlTagFormat("Recording in Progress: ", "h4"));
            recordButton.setSelected(true);
            recordButton.setText("Stop Recording");
        }

    }
}
