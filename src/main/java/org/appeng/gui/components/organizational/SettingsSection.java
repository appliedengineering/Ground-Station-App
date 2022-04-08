package org.appeng.gui.components.organizational;

import org.appeng.backend.DataManager;
import org.appeng.external.SpringUtilities;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;

public class SettingsSection extends JPanel {

    private DataManager dataManager;
    private String[] labels;
    private String[] dataIds;
    private List<SettingsTextField> textFields = new ArrayList<>();

    public SettingsSection(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void setFields(String[] labels, String[] dataIds){
        this.labels = labels;
        this.dataIds = dataIds;
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
            SettingsTextField textField = new SettingsTextField(10, dataIds[i]);
            textField.setToolTipText(dataIds[i]);
            textFields.add(textField);
        }

    }

    private class SettingsTextField extends JTextField implements DocumentListener {
        private final String dataId;
        public SettingsTextField(int columns, String dataId) {
            super(columns);
            this.dataId = dataId;
            loadExistingData();

            this.getDocument().addDocumentListener(this);
        }

        private void loadExistingData() {
            this.setText((String) dataManager.getSettingsManager().settings.get(dataId));
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            update();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            update();
        }

        private void update(){
            System.out.println("save settings");
            dataManager.getSettingsManager().settings.setProperty(dataId, this.getText());
            dataManager.getSettingsManager().saveSettings();
        }
    }
}
