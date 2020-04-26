package com.example.mobiililabra_1_2_geocoding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MainActivity extends AppCompatActivity {

    Button getLocation;
    FusedLocationProviderClient client;
    List<Address> addressList;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();
        client = LocationServices.getFusedLocationProviderClient(this);
        getLocation = findViewById(R.id.getLocationButton);
        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    return;
                }

                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){

                            double latitude, longitude;

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            TextView coordinates = findViewById(R.id.coordinateField);
                            coordinates.setText(String.format("%.2f", latitude) + ", " + String.format("%.2f", longitude));

                            try {
                                addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                Address address;
                                address = addressList.get(0);

                                String city = addressList.get(0).getLocality();
                                String country = addressList.get(0).getCountryName();

                                TextView textView = findViewById(R.id.locationNameField);
                                textView.setText(city + "\n" + country);


                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });
    }

    public void requestPermissions(){
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ACCESS_COARSE_LOCATION}, 1);
    }
}
