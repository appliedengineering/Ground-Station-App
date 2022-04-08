package org.appeng.backend;


import org.appeng.constants.DataParametersConstants;
import org.msgpack.value.Value;

import java.awt.geom.Rectangle2D;
import java.util.*;

public class DataManager {

    private static final String TAG = "DataManager";
    
    private static final long UPDATE_DELAY = 50L;
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
                while(true) {
                    if (needsUpdate) {
                        pushUpdate();
                        needsUpdate = false;
                    }
                    try {
                        Thread.sleep(UPDATE_DELAY);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }


    public void pushUpdate(){
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

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void addBoatData(Map<Value, Value> map) {

        try {
            // First convert the format of the map, make it more friendly
            List<String> keyValues = Arrays.asList(DataParametersConstants.DATA_PROPERTIES_IDS);
            long timeStamp = getTimestamp(map);

            LogUtil.add(TAG, "Got Data! with timestamp " + timeStamp);

            for (Map.Entry<Value, Value> entry : map.entrySet()) {
                Value key = entry.getKey();
                Value value = entry.getValue();

                String keyValue = key.toString();
                if (keyValues.contains(keyValue)) {
                    float entryValue = Util.parseFloat(value.toString());
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.x = timeStamp;
                    dataPoint.y = entryValue;
                    boatData.computeIfAbsent(keyValue, k -> new ArrayList<>());
                    boatData.get(keyValue).add(dataPoint);
                    // sort data
                    boatData.get(keyValue).sort(Comparator.comparing(p -> p.x));

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.addError("DataManager", "Serious error when parsing data! Corrupted data?");
        }

        needsUpdate = true;
    }

    private long getTimestamp(Map<Value, Value> map) {
        long entryValue = 0;
        for (Map.Entry<Value, Value> entry : map.entrySet()) {
            Value key = entry.getKey();
            Value value = entry.getValue();
            if (key.toString().equals("timeStamp")) {
                entryValue = (long) (Util.parseDouble(value.toString().split("E")[0]) * Math.pow(10, 12));

            }
        }
        return entryValue;
    }
}
