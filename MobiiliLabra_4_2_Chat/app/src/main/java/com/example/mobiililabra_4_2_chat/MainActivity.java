package com.example.mobiililabra_4_2_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyClientInterface {

    TextView chatField;
    Button messageSendBtn;
    EditText enterMessage;
    MyClient myClient =  null;
    //final String WSS_LINK = "wss://obscure-waters-98157.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatField = findViewById(R.id.chatViewField);
        messageSendBtn = findViewById(R.id.sendMessageButton);
        enterMessage = findViewById(R.id.enterMessageField);

        messageSendBtn.setOnClickListener(this);

        openConnection();
    }

    public void openConnection(){
        try {
            myClient = new MyClient(new URI("wss://obscure-waters-98157.herokuapp.com"), this);
            myClient.connect();
            Toast.makeText(MainActivity.this, "Eonncetion ok", Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeConnection(){
        if (myClient != null && myClient.isOpen()){
            myClient.close();
            Toast.makeText(MainActivity.this, "Connection closed", Toast.LENGTH_LONG).show();
        }
    }

    public void sendMessage(){
        if (myClient != null && myClient.isOpen()){

            String message = enterMessage.getText().toString();
            myClient.send(message);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendMessageButton){
            sendMessage();
        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatField.append(message + "\n");
            }
        });
    }

    @Override
    public void onStatusChange(final String status) {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 chatField.setText(status);
             }
         });
    }

    @Override
    public void onStop(){
        super.onStop();
        closeConnection();
    }
}
