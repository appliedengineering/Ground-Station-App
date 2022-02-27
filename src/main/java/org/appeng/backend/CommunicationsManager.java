package org.appeng.backend;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class CommunicationsManager {
    private ZContext context;
    ZMQ.Socket boatDataSocket;
    ZMQ.Socket timestampSocket;

    public void init(){
        context = new ZContext();
        boatDataSocket = context.createSocket(SocketType.SUB);
        timestampSocket = context.createSocket(SocketType.REQ);
    }
}
