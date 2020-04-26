package com.example.mobiililabra_4_1_areas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Competitions extends AppCompatActivity {

    ListView competitions;
    RequestQueue requestQueue;
    ArrayList<String> arr = new ArrayList<>();
    String addressPartOne, addressPartTwo, completeAddress;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

        competitions = findViewById(R.id.competitionsListArea);
        requestQueue = Volley.newRequestQueue(this);


        getCompetitions();

    }

    public void getCompetitions(){

        intent = getIntent();
        int id = intent.getIntExtra("EXTRAID", 0);
        addressPartOne = "https://api.football-data.org/v2/competitions?areas=";
        addressPartTwo = Integer.toString(id);
        completeAddress = addressPartOne + addressPartTwo;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, completeAddress, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray competitionArray = response.getJSONArray("competitions");

                            for (int i = 0; i < competitionArray.length(); i++){

                                JSONObject result = competitionArray.getJSONObject(i);

                                String competitionName = result.getString("name");

                                arr.add(competitionName);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Competitions.this, android.R.layout.simple_list_item_1, arr);

                                competitions.setAdapter(adapter);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}
