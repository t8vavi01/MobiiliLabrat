package com.example.mobiililabra_4_1_areas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView areaList;
    RequestQueue queue;
    ArrayList<String> arrayList =  new ArrayList<>();
    ArrayList<Integer> idList = new ArrayList<>();
    String url;
    Intent areaIntent;
    String areaName;
    int areaID;
    String extraID = "EXTRAID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        areaList = findViewById(R.id.areaListField);
        queue = Volley.newRequestQueue(this);

        getAreas();

        areaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                areaIntent = new Intent(MainActivity.this, Competitions.class);
                areaIntent.putExtra(extraID, idList.get(position));
                startActivity(areaIntent);
            }
        });
    }

    public void getAreas(){

        url = "https://api.football-data.org/v2/areas";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray areaArray = response.getJSONArray("areas");

                            for (int i = 0; i < areaArray.length(); i++){

                                JSONObject result = areaArray.getJSONObject(i);
                                areaName = result.getString("name");
                                areaID = result.getInt("id");
                                arrayList.add(areaName);
                                idList.add(areaID);

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                                areaList.setAdapter(arrayAdapter);

                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequest);

    }
}
