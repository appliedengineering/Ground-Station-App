package org.appeng.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {
    private static final long UPDATE_DELAY = 500l;
    public HashMap<String, List<DataPoint>> boatData;
    private SettingsManager settingsManager;

    public volatile boolean needsUpdate = false;

    private List<UpdateCallback> updateCallbacks = new ArrayList<>();

    public DataManager() {
        boatData = new HashMap<>();
        settingsManager = new SettingsManager();
        startPushingUpdates();
    }

    private void startPushingUpdates() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(needsUpdate) {
                    pushUpdate();
                }
                try {Thread.sleep(UPDATE_DELAY);} catch (InterruptedException e) {}
            }
        }).start();
    }


    public void pushUpdate(){
        settingsManager.saveSettings();
        for (UpdateCallback e :
                updateCallbacks) {
            e.onDataUpdate();
        }
    }

    public void registerCallback(UpdateCallback e){
        updateCallbacks.add(e);
    }

    public void init() {
        settingsManager.loadSettings();
    }

}
