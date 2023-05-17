package com.example.loginpage.student_;

import static com.example.loginpage.R.id.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.loginpage.R;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Student_Login;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudent;
import com.example.loginpage.student_.monitor_test.student_monitor_tests;
import com.example.loginpage.student_.track_syllabus.student_track_syllabus;
import com.example.loginpage.tutor_.tutor_home_screen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class student_home_screen extends AppCompatActivity {

    UtilService utilService;
    String ip;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_student_home_screen);




        email = getIntent().getStringExtra("passEmail");
        Log.i("email",email+"");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView abt_srtutor = findViewById(R.id.student_abt_srtutor);
        abt_srtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(student_home_screen.this);
                View mView = getLayoutInflater().inflate(R.layout.abt_srtut, null);

                alert.setView(mView);
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        findViewById(R.id.track_syllabus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home_screen.this, student_track_syllabus.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.monitor_tests).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home_screen.this, student_monitor_tests.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.set_geo_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilService = new UtilService();
                ip =utilService.getIp();
                final String url = "http://"+ip+":3000/api/check_if_home_location_set";

                HashMap<String,String> params =new HashMap<>();
                params.put("email",email);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                Integer statusCode = response.getInt("statusCode");

                                if(statusCode==0){
                                    Intent intent = new Intent(student_home_screen.this, student_set_home_location.class);
                                    intent.putExtra("passEmail",email);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(student_home_screen.this,"Home Location Already Set",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(student_home_screen.this,"Unable to fetch from database",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if(error instanceof ServerError && response!= null){
                            try{
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));

                                JSONObject obj = new JSONObject(res);
                                Toast.makeText(student_home_screen.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            }catch(JSONException | UnsupportedEncodingException je){
                                je.printStackTrace();
                            }
                        }

                    }
                }) {
                    public Map<String,String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> headers = new HashMap<>();
                        headers.put("Content-Type","application/json");

                        return params;
                    }
                };

                int socketTime = 3000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsObjRequest.setRetryPolicy(policy);


                VolleySingleton.getInstance(student_home_screen.this).addToRequestQueue(jsObjRequest);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1){
            SessionManagementStudent sessionManagement = new SessionManagementStudent(student_home_screen.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(student_home_screen.this, Student_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}