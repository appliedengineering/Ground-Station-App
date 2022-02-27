package org.appeng.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {
    public HashMap<String, List<DataPoint>> boatData;

    private List<UpdateCallback> updateCallbacks = new ArrayList<>();

    public DataManager() {
        boatData = new HashMap<>();
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
}
