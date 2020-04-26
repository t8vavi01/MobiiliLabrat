package com.example.mobiililabra_3_2_leagues;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    ListView leaguesList;
    RequestQueue queue;
    ArrayList<String> arrayList = new ArrayList<>();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leaguesList = findViewById(R.id.leagueList);
        queue = Volley.newRequestQueue(this);

        parseJson();
    }

    public void parseJson(){

        url = "https://api.football-data.org/v2/competitions/";

        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONArray array = response.getJSONArray("competitions");

                            for (int i = 0; i < array.length(); i++){

                                JSONObject result = array.getJSONObject(i);
                                String leagueName = result.getString("name");
                                arrayList.add(leagueName);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                                leaguesList.setAdapter(adapter);

                            }
                        } catch (JSONException e){
                            Toast.makeText(MainActivity.this, "Makin array failed", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}
