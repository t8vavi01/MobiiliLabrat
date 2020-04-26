package com.example.mobiililabra2_2_jstockjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    private static final String TAG = "TAG";
    String[] stockNames;
    String[] stockID;
    int index;
    String[] priceList = {"", "", "", "", "", ""};
    RequestQueue requestQueue;
    String[] finalArray = {"", "", "", "", "", ""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        stockNames = resources.getStringArray(R.array.stockNames);
        stockID = resources.getStringArray(R.array.StockIDs);
        listView = findViewById(R.id.stockList);

        requestQueue = Volley.newRequestQueue(this);

        listView = findViewById(R.id.stockList);




        for (index = 0; index <= 5; index++) {
            parseJson(index);
        }

    }

    public void parseJson(final int i){

        textView = findViewById(R.id.testField);


            String urlPartOne = "https://financialmodelingprep.com/api/company/price/";
            String urlPartTwo = "?datatype=json";

                String completeUrl = urlPartOne + stockID[i] + urlPartTwo;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                        JSONObject result = response.getJSONObject(stockID[i]);
                                        priceList[i] = result.getString("price");

                                        finalArray[i] = stockNames[i] + ": " + priceList[i];

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, finalArray);
                                    listView.setAdapter(arrayAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Log.d(TAG, "Response went to crappers");
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Toast.makeText(MainActivity.this, "Cannot connect to API", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(request);
    }
}
