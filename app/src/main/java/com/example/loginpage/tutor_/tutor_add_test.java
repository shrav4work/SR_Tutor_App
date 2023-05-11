package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.loginpage.student_.student_home_screen;
import com.example.loginpage.student_.student_set_home_location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class tutor_add_test extends AppCompatActivity {

    String ip;
    UtilService utilService;
    EditText topic_name,date_of_test,marks_scored,max_marks;
    String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_add_test);

        subject = getIntent().getStringExtra("subjectSelected");
//        Log.i("subject",subject);
        topic_name=findViewById(R.id.topic_name);
        date_of_test=findViewById(R.id.date_of_test);
        marks_scored=findViewById(R.id.marks_scored);
        max_marks=findViewById(R.id.max_marks);

        findViewById(R.id.submit_addtest_tutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilService = new UtilService();
                ip =utilService.getIp();
                final String url = "http://"+ip+":3000/api/test_details_update";

                HashMap<String,String> params =new HashMap<>();
                params.put("subject",subject);
                params.put("topic",topic_name.getText().toString());
                params.put("date",date_of_test.getText().toString());
                params.put("marks",marks_scored.getText().toString());
                params.put("max_marks",max_marks.getText().toString());
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                Toast.makeText(tutor_add_test.this,response.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            else{
                                Toast.makeText(tutor_add_test.this,response.getString("msg"),Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(tutor_add_test.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
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


                VolleySingleton.getInstance(tutor_add_test.this).addToRequestQueue(jsObjRequest);
                Toast.makeText(tutor_add_test.this, "test added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tutor_add_test.this,tutor_about_tests.class);
                startActivity(intent);

            }
        });

    }
}