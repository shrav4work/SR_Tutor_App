package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.login_pages.Student_Login;
import com.example.loginpage.student_.student_home_screen;
import com.example.loginpage.tutor_.tutor_geo_signin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Reset_Password extends AppCompatActivity {

    EditText email;
    UtilService utilService;
    String ip;
    private String reset_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        utilService = new UtilService();
        ip =utilService.getIp();
        Log.i("IP",ip);
        email = findViewById(R.id.email_for_reset);
        findViewById(R.id.send_reset_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_email =email.getText().toString();
                if(validate(v)){
                    sendResetLink();
                }
            }
        });


    }

    private void sendResetLink() {
        HashMap<String,String> params =new HashMap<>();
        params.put("email",reset_email);

        final RequestQueue queue = Volley.newRequestQueue(Reset_Password.this);
        final String url = "http://"+ip+":3000/api/forgot-password";

        queue.start();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        String message = response.getString("msg");
                        Toast.makeText(Reset_Password.this,message,Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(Reset_Password.this,"Email not registered",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Reset_Password.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
        if(!TextUtils.isEmpty(reset_email)){
            isValid = true;
        } else{
            utilService.showSnackBar(view,"Please enter your username");
        }
        return isValid;
    }
}