package org.appeng.backend;

import org.appeng.constants.SettingsConstants;
import org.appeng.data.BoatTripData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SettingsManager {
    public Properties settings = new Properties();


    public void saveSettings(DataManager dataManager){
        settings.setProperty(SettingsConstants.recordingDataSerialized, serializeBoatTripData(dataManager.boatTripData));
        try {
            settings.store(new FileOutputStream("settings.config"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings(DataManager dataManager){
        try {
            settings.load(new FileInputStream("settings.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // on start up, reset values
        settings.setProperty(SettingsConstants.currentRecordingId, "null");
        String recordingIds = settings.getProperty(SettingsConstants.recordingDataSerialized);
        if(recordingIds != null) {
            dataManager.boatTripData = parseBoatTripData(recordingIds, dataManager);
        }
        for(BoatTripData trip : dataManager.boatTripData) {
            System.out.println(trip.toString());
        }
        saveSettings(dataManager);
    }

    private List<BoatTripData> parseBoatTripData(String data, DataManager dataManager) {
        List<BoatTripData> boatTripDataList = new ArrayList<>();

        if(data.equals("null")){
            return boatTripDataList;
        }

        for (String dataToken : data.split("\"\"")) {
            boatTripDataList.add(new BoatTripData(dataToken, dataManager));
        }
        return boatTripDataList;
    }

    private String serializeBoatTripData(List<BoatTripData> data) {
        if(data.size() == 0){
            return "null";
        }
        StringBuilder text = new StringBuilder();
        for (BoatTripData boatTrip : data) {
            text.append(boatTrip.toString()).append("\"\"");
        }
        return text.toString();
    }

}
