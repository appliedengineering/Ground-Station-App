package org.appeng.backend;

public class Backend {

    private DataManager dataManager;
    private CommunicationsManager communicationsManager;

    public void init(){
        dataManager = new DataManager();
        communicationsManager = new CommunicationsManager();

        dataManager.init();
        communicationsManager.init();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CommunicationsManager getCommunicationsManager() {
        return communicationsManager;
    }
}
