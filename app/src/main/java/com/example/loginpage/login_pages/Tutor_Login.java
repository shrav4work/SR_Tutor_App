package com.example.loginpage.login_pages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginpage.R;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.tutor_.Tutor_actual_geo_signin;
import com.example.loginpage.tutor_.tutor_geo_signin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Tutor_Login extends AppCompatActivity {


    private EditText username;
    private EditText password;
    String ip;

    UtilService utilService;
    private String tutor_email, tutor_password;

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(Tutor_Login.this);
        if(sessionManagement.getSESSION_KEY() == null){
            Toast.makeText(this, "No USER LOGIN FOUND. PLEASE LOGIN", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "User Already Logged in", Toast.LENGTH_SHORT).show();
            String logged_in_email = sessionManagement.getSESSION_KEY();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_tutor_login);
        TextView abt_srtutor = findViewById(R.id.tutor_login_abt_srtutor);
        username = findViewById(R.id.tutor_user);
        password = findViewById(R.id.tutor_pass);
        utilService = new UtilService();
        ip = utilService.getIp();
        Log.i("IP",ip);

        findViewById(R.id.login_tutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutor_email = username.getText().toString();
                tutor_password = password.getText().toString();
                if(validate(view)){
                    loginUser();
                }
            }
        });
        abt_srtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Tutor_Login.this);
                View mView = getLayoutInflater().inflate(R.layout.abt_srtut, null);

                alert.setView(mView);
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

    }
    private void loginUser() {
        HashMap<String,String> params =new HashMap<>();
        params.put("email",tutor_email);
        params.put("password",tutor_password);

        Log.i("TAG1",params+"");

        final RequestQueue queue = Volley.newRequestQueue(Tutor_Login.this);
        final String url = "http://"+ip+":3000/api/login/tutor";

        queue.start();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        SessionManagement sessionManagement = new SessionManagement(Tutor_Login.this);
                        sessionManagement.SaveSession(tutor_email);

//                        String token = response.getString("token");
//                        Toast.makeText(Tutor_Login.this,token,Toast.LENGTH_SHORT).show();
                        String lat = response.getString("lat");
                        String lon = response.getString("lon");
                        Log.i("Lat, Lon",lat+" "+lon);

                        if(lat.isEmpty() && lon.isEmpty()){
                            Intent intent = new Intent(Tutor_Login.this, tutor_geo_signin.class);
                            intent.putExtra("passEmail",response.getString("email"));
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(Tutor_Login.this, Tutor_actual_geo_signin.class);
                            startActivity(intent);
                        }

                    }
                    else{
                        Toast.makeText(Tutor_Login.this,"Invalid Username or password",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Tutor_Login.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
        queue.add(jsObjRequest);
    }
    public boolean validate(View view){
        boolean isValid = false;
        if(!TextUtils.isEmpty(tutor_email)){
            isValid = true;
            if(!TextUtils.isEmpty(tutor_password)){
                isValid = true;
            } else{
                utilService.showSnackBar(view,"Please enter your password");
            }
        } else{
            utilService.showSnackBar(view,"Please enter your username");
        }
        return isValid;
    }
}