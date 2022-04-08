package org.appeng.backend;

import org.appeng.gui.components.organizational.SettingsPane;
import org.appeng.gui.components.organizational.SettingsSection;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.MapValue;
import org.msgpack.value.Value;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import java.io.IOException;
import java.security.cert.TrustAnchor;
import java.util.Map;

public class BoatDataManager implements Runnable {

    private static final String TAG = "BoatDataManager";

    private ZContext context;
    private ZMQ.Socket boatDataSocket;
    private DataManager dataManager;

    public volatile boolean isRunning;

    public BoatDataManager(ZContext context, ZMQ.Socket boatDataSocket, DataManager dataManager) {
        this.context = context;
        this.boatDataSocket = boatDataSocket;
        this.dataManager = dataManager;
    }

    public boolean connect(String connectionString) {
        try {
            boatDataSocket.connect(connectionString);
            boatDataSocket.subscribe("".getBytes());
            LogUtil.add(TAG, "Connected!");
        } catch (Exception e) {
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
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(data);
        MapValue mv = null;
        try {
            mv = (MapValue) unpacker.unpackValue();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<Value, Value> map = mv.map();

        dataManager.addBoatData(map);
    }

    @Override
    public void run() {
        String ip = (String) dataManager.getSettingsManager().settings.get(SettingsPane.boatDataNetworkDataIds[0]);
        String port = (String) dataManager.getSettingsManager().settings.get(SettingsPane.boatDataNetworkDataIds[1]);
        String connectionString = "tcp://" + ip + ":" + port;
        connect(connectionString);
        isRunning = true;
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
