package org.appeng.backend;

public class Backend {

    private DataManager dataManager;
    private CommunicationsManager communicationsManager;

    public void init(){
        dataManager = new DataManager();
        communicationsManager = new CommunicationsManager(this);

        dataManager.init();
        communicationsManager.init();

    }

    public void start(){
        communicationsManager.start();
        dataManager.start();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CommunicationsManager getCommunicationsManager() {
        return communicationsManager;
    }
}
