package com.example.loginpage.tutor_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.main_screen.MainActivity;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudentInTutor;
import com.example.loginpage.student_.student_home_screen;
import com.example.loginpage.student_.student_set_home_location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class tutor_add_test extends AppCompatActivity {

    String ip;
    UtilService utilService;
    EditText topic_name,date_of_test,marks_scored,max_marks;
    String subject;

    String test_name, date, marks, max_mark;

    private static final String TAG = "MainActivity";

    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_add_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Select Date
        mDisplayDate = (EditText) findViewById(R.id.date_of_test);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        tutor_add_test.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };





        subject = getIntent().getStringExtra("subjectSelected");
//        Log.i("subject",subject);
        topic_name=findViewById(R.id.topic_name);
        date_of_test=findViewById(R.id.date_of_test);
        marks_scored=findViewById(R.id.marks_scored);
        max_marks=findViewById(R.id.max_marks);


        findViewById(R.id.submit_addtest_tutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_name = topic_name.getText().toString();
                date = date_of_test.getText().toString();
                marks = marks_scored.getText().toString();
                max_mark = max_marks.getText().toString();
                if (validate(v)) {
                    submitTest();
                }
            }
        });
    }

    private void submitTest() {
        utilService = new UtilService();
        ip =utilService.getIp();
        SessionManagementStudentInTutor sessionManagementStudentInTutor = new SessionManagementStudentInTutor(tutor_add_test.this);
        String id = sessionManagementStudentInTutor.getSESSION_KEY();
        Log.i("Student iD",id);
        final String url = "http://"+ip+":3000/api/test_details_update/"+id;

        HashMap<String,String> params =new HashMap<>();
        params.put("subject",subject);
        params.put("test_name",topic_name.getText().toString());
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


    public boolean validate(View view){
        boolean isValid = false;
        if(!TextUtils.isEmpty(test_name)) {
            isValid = true;
            if (!TextUtils.isEmpty(date)) {
                isValid = true;
                if (!TextUtils.isEmpty(marks)) {
                    isValid = true;
                    if (!TextUtils.isEmpty(max_mark)) {
                        isValid = true;
                    } else {
                        utilService.showSnackBar(view, "Please enter your max marks");
                    }
                } else {
                    utilService.showSnackBar(view, "Please enter your marks scored");
                }
            } else {
                utilService.showSnackBar(view, "Please enter your date of test");
            }
        } else{
            utilService.showSnackBar(view,"Please enter your test topic");
        }
        return isValid;
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
            SessionManagement sessionManagement = new SessionManagement(tutor_add_test.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(tutor_add_test.this, Tutor_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}