package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.external.SpringUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkingSettingsSection extends JPanel {

    private DataManager dataManager;
    private String[] labels = {"IP Address: ", "Port: "};
    private String[] dataIds = {"boatDataIpAddress", "boatDataPort"};
    private List<SettingsTextField> textFields = new ArrayList<>();

    public NetworkingSettingsSection(DataManager dataManager) {
        this.dataManager = dataManager;

        init();
    }

    public void init() {
        initTextFields();

        int numPairs = labels.length;

        //Create and populate the panel.
        this.setLayout(new SpringLayout());
        this.setOpaque(false);
        int padding = 10;
        this.setBorder(new CompoundBorder(
                new TitledBorder("Boat Data Networking"),
                new EmptyBorder(padding, padding, padding, padding)));
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            this.add(l);
            JTextField textField = textFields.get(i);
            l.setLabelFor(textField);
            this.add(textField);
        }

        int spacing = 10;

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
                numPairs, 2, //rows, cols
                spacing, spacing,        //initX, initY
                spacing, spacing);       //xPad, yPad

    }

    private void initTextFields() {
        for (int i = 0; i < dataIds.length; i++) {
            SettingsTextField textField = new SettingsTextField(10);
            textField.setToolTipText(dataIds[i]);
            textFields.add(textField);
        }

    }

    private class SettingsTextField extends JTextField {

        public SettingsTextField(int columns) {
            super(columns);
            
            loadExistingData();
        }

        private void loadExistingData() {
        }
    }
}
