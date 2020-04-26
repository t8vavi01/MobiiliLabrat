package com.example.mobiililabra2_1_async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.dom.DOMLocator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText enterWebsite;
    Button goToWebSite;
    String address, webDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToWebSite = findViewById(R.id.getWebsiteButton);
        goToWebSite.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getWebsiteButton){

            enterWebsite = findViewById(R.id.enterWebSite);

            address = "https://" + enterWebsite.getText().toString();
            webDisplay = "";

            new LoadWebPage().execute(address, webDisplay);


        }
    }

    private class LoadWebPage extends AsyncTask<String, Void, String>{

        TextView textView = findViewById(R.id.webContentField);

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String htmlStream = strings[1];

            try{
                URL url = new URL(address);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                htmlStream = GetHTMLString.htmlString(inputStream);


            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

            return htmlStream;
        }

        @Override
        protected void onPostExecute(String result){

            textView.setText(result);

            super.onPostExecute(result);
        }
    }


}
