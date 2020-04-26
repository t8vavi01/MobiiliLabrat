package com.example.mobiililabra2_3_enterstock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> stocks = new ArrayList<>();
    private ListView listView;
    String urlPartOne = "https://financialmodelingprep.com/api/company/price/";
    String urlPartTwo = "?datatype=json";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.showStockButton).setOnClickListener(this);
        listView = findViewById(R.id.stockList);
        queue = Volley.newRequestQueue(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.showStockButton) {
            EditText nameEditor = findViewById(R.id.enterStockNameField);
            EditText idEditor = findViewById(R.id.enterStockIDField);
            final String name = nameEditor.getText().toString();
            final String id = idEditor.getText().toString();

            if (name != null && name.length() > 0){

                String completeUrl = urlPartOne + id + urlPartTwo;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject result = response.getJSONObject(id);

                                    String price = result.getString("price");

                                    String finalString = name + ": " + price;

                                    stocks.add(finalString);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stocks);
                                    listView.setAdapter(adapter);

                                } catch (JSONException e){
                                    Toast.makeText(MainActivity.this, "Object response failure", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                            }

                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Response error!", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                });

                queue.add(request);

            }
        }
    }
}
