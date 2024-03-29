package org.appeng.backend;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class CommunicationsManager {
    private ZContext context;
    ZMQ.Socket boatDataSocket;
    ZMQ.Socket timestampSocket;

    private BoatDataManager boatDataManager;
    private Thread boatDataReceiverThread;
    private Backend backend;

    public CommunicationsManager(Backend backend) {
        this.backend = backend;
    }

    public void init(){
        context = new ZContext();
        boatDataSocket = context.createSocket(SocketType.SUB);
        timestampSocket = context.createSocket(SocketType.REQ);
    }


    public void start() {
        startReceivingBoatData();
    }

    public void startReceivingBoatData(){
        boatDataManager = new BoatDataManager(context, boatDataSocket, backend.getDataManager());
        boatDataReceiverThread = new Thread(boatDataManager);
        boatDataReceiverThread.start();
    }

    public void restartNetworking() throws InterruptedException {
        boatDataManager.isRunning = false;
        boatDataReceiverThread.interrupt();
        boatDataReceiverThread.join();

        boatDataReceiverThread = new Thread(boatDataManager);
        boatDataReceiverThread.start();
    }
}
