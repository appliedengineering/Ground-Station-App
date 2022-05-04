package org.appeng.backend;


import org.appeng.constants.DataParametersConstants;
import org.appeng.data.BoatTripData;
import org.msgpack.value.Value;

import java.util.*;

public class DataManager {

    private static final String TAG = "DataManager";
    
    private static final long UPDATE_DELAY = 50L;

    public List<BoatTripData> boatTripData = new ArrayList<>();

    public HashMap<String, List<DataPoint>> boatData;
    public HashMap<String, Boolean> boatDataBool;
    public String debugText = "";
    private SettingsManager settingsManager;

    public volatile int boatDataStatus = -1; // -1 = no data, 0 = data

    public volatile boolean needsUpdate = false;

    private final List<UpdateCallback> updateCallbacks = new ArrayList<>();
    private final List<UpdateCallback> updateConfigCallbacks = new ArrayList<>();


    public DataManager() {
        boatData = new HashMap<>();
        boatDataBool = new HashMap<>();
        settingsManager = new SettingsManager();
        startPushingUpdates();
    }

    private void startPushingUpdates() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    // if (needsUpdate) {
                        pushUpdate();
                        needsUpdate = false;
                    // }
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

    public void pushConfigUpdate(){
        for (UpdateCallback e :
                updateConfigCallbacks) {
            e.onDataUpdate();
        }
    }

    public void registerCallback(UpdateCallback e){
        updateCallbacks.add(e);
    }

    public void registerConfigCallback(UpdateCallback e){
        updateConfigCallbacks.add(e);
    }

    public void init() {
        settingsManager.loadSettings(this);
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void addBoatData(Map<Value, Value> map) {

        try {
            // First convert the format of the map, make it more friendly
            List<String> keyValues = Arrays.asList(DataParametersConstants.DATA_PROPERTIES_IDS);
            List<String> boolKeyValues = Arrays.asList(DataParametersConstants.BOOL_PROPERTIES_IDS);
            long timeStamp = getTimestamp(map);

            LogUtil.add(TAG, "Got Data! with timestamp " + timeStamp, this);

            for (Map.Entry<Value, Value> entry : map.entrySet()) {
                Value key = entry.getKey();
                Value value = entry.getValue();

                String keyValue = key.toString();
                if (keyValues.contains(keyValue)) {
                    float entryValue = Util.parseFloat(value.toString(), this);
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.x = timeStamp;
                    dataPoint.y = entryValue;
                    boatData.computeIfAbsent(keyValue, k -> new ArrayList<>());
                    boatData.get(keyValue).add(dataPoint);
                    // sort data
                    boatData.get(keyValue).sort(Comparator.comparing(p -> p.x));

                } else if (boolKeyValues.contains(keyValue)) {
                    boolean entryBool = Util.parseBoolean(value.toString(), this);
                    boatDataBool.put(keyValue, entryBool);
                }




            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.addError("DataManager", "Serious error when parsing data! Corrupted data?", this);
        }

        pushUpdate();
    }

    private long getTimestamp(Map<Value, Value> map) {
        long entryValue = 0;
        for (Map.Entry<Value, Value> entry : map.entrySet()) {
            Value key = entry.getKey();
            Value value = entry.getValue();
            if (key.toString().equals("timeStamp")) {
                entryValue = (long) (Util.parseDouble(value.toString().split("E")[0], this) * Math.pow(10, 12));

            }
        }
        return entryValue;
    }


}
