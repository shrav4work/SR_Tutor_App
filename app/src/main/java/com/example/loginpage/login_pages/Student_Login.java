package com.example.loginpage.login_pages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.loginpage.Reset_Password;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.main_screen.MainActivity;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudent;
import com.example.loginpage.student_.student_home_screen;
import com.example.loginpage.student_.student_set_home_location;
import com.example.loginpage.tutor_.Tutor_actual_geo_signin;
import com.example.loginpage.tutor_.tutor_geo_signin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Student_Login extends AppCompatActivity {

    EditText student_username ;
    EditText student_password ;
    String ip;


    TextView forgotPasswordStudent;

    UtilService utilService;
    private String username, password;


    @Override
    protected void onStart() {
        super.onStart();
        SessionManagementStudent sessionManagement = new SessionManagementStudent(Student_Login.this);
        String mailSession = sessionManagement.getSESSION_KEY();
        if(mailSession != null){

            Intent intent = new Intent(Student_Login.this, student_home_screen.class);
            intent.putExtra("passEmail",mailSession);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
//            Toast.makeText(this, "User Already Logged in", Toast.LENGTH_SHORT).show();
//            String logged_in_email = sessionManagement.getSESSION_KEY();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_student_login);

        TextView abt_srtutor = findViewById(R.id.abt_srtutor_std_login);
        student_username = findViewById(R.id.student_user);
        student_password = findViewById(R.id.student_pass);
//        forgotPasswordStudent=findViewById(R.id.forgot_password_student);
        utilService = new UtilService();
        ip =utilService.getIp();
        Log.i("IP",ip);

//        forgotPasswordStudent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Student_Login.this, Reset_Password.class));
//            }
//        });





        findViewById(R.id.login_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = student_username.getText().toString();
                password = student_password.getText().toString();
                closeKeyboard();
                if(validate(view)){
                    loginUser();
                }


            }
        });
        abt_srtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Student_Login.this);
                View mView = getLayoutInflater().inflate(R.layout.abt_srtut, null);

                alert.setView(mView);
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

    }

    private void loginUser() {
        HashMap<String,String> params =new HashMap<>();
        params.put("email",username);
        params.put("password",password);

        Log.i("TAG1",params+"");

        final RequestQueue queue = Volley.newRequestQueue(Student_Login.this);
        final String url = "http://"+ip+":3000/api/login/student";

        queue.start();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        String token = response.getString("token");
//                        Toast.makeText(Student_Login.this,token,Toast.LENGTH_SHORT).show();

                        SessionManagementStudent sessionManagement = new SessionManagementStudent(Student_Login.this);
                        sessionManagement.SaveSession(username);
//                        sessionManagement.setEmail(username);

//                        getCoordinates(response.getString("email"));
                        String lat = response.getString("lat");
                        String lon = response.getString("lon");
                        Log.i("Lat, Lon",lat+" "+lon);

                        if(lat.isEmpty() && lon.isEmpty()){
                            Intent intent = new Intent(Student_Login.this, student_set_home_location.class);
                            intent.putExtra("passEmail",response.getString("email"));
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(Student_Login.this, student_home_screen.class);
                            intent.putExtra("passEmail",response.getString("email"));
                            startActivity(intent);
                        }

                    }
                    else{
                        Toast.makeText(Student_Login.this,"Invalid Username or password",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Student_Login.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
                    }catch(JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }

            }
        }) {
            public Map<String,String> getHeaders() throws AuthFailureError{
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
        if(!TextUtils.isEmpty(username)){
            isValid = true;
            if(!TextUtils.isEmpty(password)){
                isValid = true;
            } else{
                utilService.showSnackBar(view,"Please enter your password");
            }
        } else{
            utilService.showSnackBar(view,"Please enter your username");
        }
        return isValid;
    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }



}