package com.example.mobiililabra_1_3_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText enterWebsite;
    Button goToWebSite;
    TextView showWebsite;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        enterWebsite = findViewById(R.id.enterWebSite);
        goToWebSite = findViewById(R.id.getWebsiteButton);

        goToWebSite.setOnClickListener(this);


    }

    public void loadWebPage(String siteUrl) {

        try{
            URL url = new URL(siteUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            String webSiteHTMLText = GetHTMLString.htmlString(inputStream);

            showWebsite = findViewById(R.id.webContentField);
            showWebsite.setText(webSiteHTMLText);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.getWebsiteButton) {

            enterWebsite = findViewById(R.id.enterWebSite);
            address = "https://" + enterWebsite.getText().toString();

            loadWebPage(address);
        }

    }

}
