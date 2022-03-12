package org.appeng.backend;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import java.io.IOException;
import java.security.cert.TrustAnchor;

public class BoatDataManager implements Runnable {

    private static final String TAG = "BoatDataManager";

    private ZContext context;
    private ZMQ.Socket boatDataSocket;

    public volatile boolean isRunning;

    public BoatDataManager(ZContext context, ZMQ.Socket boatDataSocket) {
        this.context = context;
        this.boatDataSocket = boatDataSocket;
    }

    public boolean connect(String connectionString) {
        try {
            boatDataSocket.connect(connectionString);
            boatDataSocket.subscribe("".getBytes());
        } catch (ZMQException e) {
            LogUtil.addError(TAG, "Connect error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public byte[] receiveData() throws ZMQException {
        byte[] buffer = null;
        try {
            buffer = boatDataSocket.recv(ZMQ.NOBLOCK);
        } catch (ZMQException e) {
            LogUtil.addError(TAG, "Receive error: " + e.getMessage());
        }
        return buffer;
    }

    public void handleData(byte[] data){

    }

    @Override
    public void run() {
        // connect();
        while (isRunning) {
            try {
                byte[] data = receiveData();
                if (data != null) {
                    handleData(data);
                }
            } catch (ZMQException e) {
                LogUtil.addError(TAG, "Network Error: " + e.getMessage());
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
