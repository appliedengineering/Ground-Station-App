package org.appeng.backend;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class CommunicationsManager {
    private ZContext context;
    ZMQ.Socket boatDataSocket;
    ZMQ.Socket timestampSocket;

    private Runnable boatDataManager;
    private Thread boatDataReceiverThread;
    private Backend backend;

    public void init(){
        context = new ZContext();
        boatDataSocket = context.createSocket(SocketType.SUB);
        timestampSocket = context.createSocket(SocketType.REQ);
    }


    public void start() {
        startReceivingBoatData();
    }

    public void startReceivingBoatData(){
        boatDataManager = new BoatDataManager(context, boatDataSocket);
        boatDataReceiverThread = new Thread(boatDataManager);
        boatDataReceiverThread.start();
    }
}
