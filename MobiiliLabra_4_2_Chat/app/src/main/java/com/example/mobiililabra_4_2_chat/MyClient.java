package com.example.mobiililabra_4_2_chat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface MyClientInterface{
    void onMessage(String message);
    void onStatusChange(String status);
        }

public class MyClient extends WebSocketClient {

    MyClientInterface myClientInterface;

    public MyClient(URI serverUri, MyClientInterface inter){
        super(serverUri);
        this.myClientInterface = inter;
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        myClientInterface.onStatusChange("Connection opened");
    }

    @Override
    public void onMessage(String message) {
        myClientInterface.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        myClientInterface.onStatusChange("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        myClientInterface.onStatusChange("Error in socket: " + ex.toString());
    }
}
