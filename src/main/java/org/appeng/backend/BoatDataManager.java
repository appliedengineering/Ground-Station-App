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

    private String connectionString;

    public volatile boolean isRunning;

    public int noDataCount = 0;

    public BoatDataManager(ZContext context, ZMQ.Socket boatDataSocket, DataManager dataManager) {
        this.context = context;
        this.boatDataSocket = boatDataSocket;
        this.dataManager = dataManager;
    }

    public boolean connect(String connectionString) {
        try {
            this.connectionString = connectionString;
            boatDataSocket.connect(connectionString);
            boatDataSocket.subscribe("".getBytes());
            LogUtil.add(TAG, "Connected!", dataManager);
        } catch (Exception e) {
            LogUtil.addError(TAG, "Connect error: " + e.getMessage(), dataManager);
            return false;
        }
        return true;
    }

    public byte[] receiveData() throws ZMQException {
        byte[] buffer = null;
        try {
            buffer = boatDataSocket.recv(ZMQ.NOBLOCK);
        } catch (ZMQException e) {
            LogUtil.addError(TAG, "Receive error: " + e.getMessage(), dataManager);
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
                    noDataCount = 0;
                    dataManager.boatDataStatus = 0;
                    dataManager.pushConfigUpdate();
                } else {
                    // LogUtil.add(TAG, "No Data!", dataManager);
                    noDataCount++;
                    if(noDataCount > 10) {
                        dataManager.boatDataStatus = -1;
                        dataManager.pushConfigUpdate();
                    }
                }
            } catch (ZMQException e) {
                LogUtil.addError(TAG, "Network Error: " + e.getMessage(), dataManager);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }

        disconnect();
    }

    private void disconnect() {
        if(connectionString == null) return;
        try {
            boatDataSocket.disconnect(connectionString);
        }catch (ZMQException e) {
            // unknown host
        }
    }
}
