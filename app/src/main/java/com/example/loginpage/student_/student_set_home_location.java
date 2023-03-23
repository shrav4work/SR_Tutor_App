package com.example.loginpage.student_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginpage.R;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.tutor_.tutor_geo_signin;
import com.example.loginpage.tutor_.tutor_home_screen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class student_set_home_location extends AppCompatActivity {

    private GoogleMap mMap;
    double calcDistance;
    int flag=0;
    String ip;

    UtilService utilService;
    private static final int FINE_LOCATION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient locationClient;
    String passedEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_set_home_location);

        utilService = new UtilService();
        ip =utilService.getIp();
        Log.i("IP",ip);
        passedEmail = getIntent().getStringExtra("passEmail");
        Log.i("passedEmail",passedEmail);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                showCurrentLocation();
            }
        });
        prepareLocationServcies();


        findViewById(R.id.set_home_location_student).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    Intent intent = new Intent(student_set_home_location.this,student_home_screen.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(student_set_home_location.this,"Home location not set properly.Please Try again..",Toast.LENGTH_SHORT).show();
                }

            }
        }));

        findViewById(R.id.skip_home_loc).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_set_home_location.this, student_home_screen.class);
                intent.putExtra("passEmail", passedEmail);
                startActivity(intent);
            }

        }));

    }
    public void getLocationPermission(){
        ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == FINE_LOCATION_REQUEST_CODE){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCurrentLocation();
            }
            else {
                Toast.makeText(this," User denied location access",Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void showCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            getLocationPermission();
        }
        else
        {
            locationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null){
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        System.out.println("Location Coordinates " + location.getLatitude() + "  " + location.getLongitude());
                        Log.i("Test","Test Message");
                        Log.i("TAG Latitude",location.getLatitude() + "");
                        Log.i("TAG Longitude", location.getLongitude()+ "");


                        // ------------------------------------------------------------------------------------------

                        final RequestQueue queue = Volley.newRequestQueue(student_set_home_location.this);
                        final String url = "http://"+ip+":3000/api/home_loc_student";


                        HashMap<String,String> params =new HashMap<>();
                        params.put("lat",String.valueOf(location.getLatitude()));
                        params.put("lon",String.valueOf(location.getLongitude()));
                        params.put("email",passedEmail);

                        Log.i("lat,lon",params+"");

                        queue.start();

                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                                url,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if(response.getBoolean("success")) {
                                                String message = response.getString("msg");
//                                                Double latt =Double.parseDouble(response.getString("lat"));
//                                                Double lonn =Double.parseDouble(response.getString("lon"));
                                                Log.i("locationret",message);
                                                flag=1;
                                            }
                                        } catch (JSONException e) {
                                            Log.i("Error",e.getMessage()+"");
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof ClientError) {
                                    // Handle ClientError
                                    Log.e("TAG2", "Client Error: " + error);
                                } else {
                                    // Handle other errors
                                    Log.e("TAG3", "Error: " + error);
                                }
                            }
                        });

                        queue.add(jsObjRequest);

                        //---------------------------------------------------------------------------------------------




                        Log.i("Data type",((Object)location.getLatitude()).getClass().getSimpleName()+ "");
//                        Log.i("Calculated Distance",calcDistance + " meters");
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
//                         CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng,16.0f);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17.0f));
                    }
                    else {
                        Toast.makeText(student_set_home_location.this,"Something went wrong, Please try agin",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void prepareLocationServcies(){
        locationClient = LocationServices.getFusedLocationProviderClient(this);

    }

}