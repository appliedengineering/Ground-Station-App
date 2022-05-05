package org.appeng.data;

import org.appeng.backend.DataManager;
import org.appeng.backend.LogUtil;
import org.appeng.backend.Util;
import org.appeng.gui.components.organizational.BoatTripRecording;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public class BoatTripData {

    private static final String TAG = "BoatTripData";

    private String tripId = "id";
    private String tripName = "name";
    private int status; // -1 = has not recorded, 0 = finished recording, 1 = during recording


    public BoatTripData(String serializedData, DataManager dataManager) {
        try {
            String[] tokens = serializedData.split(";");
            tripId = tokens[0];
            tripName = tokens[1];
            status = (int) Util.parseFloat(tokens[2], dataManager);
        }catch (Exception e) {
            LogUtil.addError(TAG, "Failed to parse boat trip data (corrupted?)", dataManager);
        }

    }

    public BoatTripData(){
        tripId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.US));
        tripName = formattedTime;
        status = -1;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%d", tripId, tripName, status);
    }


    // GETTERS AND SETTERS


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
