package com.example.mobiililabra_3_1_volleyhtml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button getHTML;
    EditText enterHTML;
    TextView htmlView;
    String strHTML;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHTML = findViewById(R.id.getWebSiteButton);
        enterHTML = findViewById(R.id.enterWebSiteField);
        htmlView = findViewById(R.id.htmlField);

        getHTML.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(MainActivity.this);

    }

    public void getPage(){

        strHTML = "https://" + enterHTML.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, strHTML,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            htmlView.setText(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        getPage();
    }
}
