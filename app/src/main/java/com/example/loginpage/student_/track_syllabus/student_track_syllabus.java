package com.example.loginpage.student_.track_syllabus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.loginpage.R;
import com.example.loginpage.StudentAdapter;
import com.example.loginpage.Student_Details;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Student_Login;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudent;
import com.example.loginpage.tutor_.student_list;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class student_track_syllabus extends AppCompatActivity {

    TextInputLayout studentIdInput;
    AutoCompleteTextView studentIdAuto;
    ArrayList<String> studentIdList;
    ArrayAdapter<String> studentListAdapter;
    UtilService utilService;
    String ip;

    String[] item = {"English","Maths","Kannada","Science","Social Science","Maths"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    String itemSelected;

    ArrayList<Student_Details> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_track_syllabus);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompleteTextView =findViewById(R.id.autoCompleteTextView_select_subject_student_details);
        adapter = new ArrayAdapter<String>(this,R.layout.drop_down,item);
        autoCompleteTextView.setAdapter(adapter);

        ListView student_list_view = findViewById(R.id.student_list_view);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = parent.getItemAtPosition(position).toString();
                setData(student_list_view,itemSelected);
            }
        });

        studentIdInput = (TextInputLayout)findViewById(R.id.student_id_text);
        studentIdAuto = (AutoCompleteTextView)findViewById(R.id.student_list_text);
        studentIdInput.setHint("Enter Student ID");
        studentIdList = new ArrayList<>();
        studentIdList.add("01fe19bcs144");
        studentIdList.add("02fe19bcs120");
        studentIdList.add("01fe19bme013");
        studentListAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,studentIdList);
        studentIdAuto.setAdapter(studentListAdapter);
        studentIdAuto.setThreshold(2);
    }


    private void setData(ListView student_list_view, String itemSelected) {
        utilService = new UtilService();
        ip =utilService.getIp();

        final String url = "http://"+ip+":3000/api/student_details/01fe19bcs060/"+itemSelected;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Student_Details s1 = new Student_Details(
                            response.optString("classs",""),
                            response.getString("subject"),
                            "A DIV",
                            response.getString("board"),
                            response.getString("class_planned"),
                            response.getString("class_engaged"),
                            response.getString("class_pending"),
                            response.getString("portion_covered"),
                            response.getString("portion_pending"));

                    studentList.add(s1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                StudentAdapter adapter = new StudentAdapter(student_track_syllabus.this,R.layout.student_list_adapter,studentList);
                student_list_view.setAdapter(adapter);
                closeKeyboard();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    // Handle network error
                    Toast.makeText(student_track_syllabus.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    // Handle server error
                    int statusCode = error.networkResponse != null ? error.networkResponse.statusCode : 0;
                    if (statusCode == 404) {
                        Toast.makeText(student_track_syllabus.this, "Subject not found for the student", Toast.LENGTH_SHORT).show();
                    } else if (statusCode == 401) {
                        Toast.makeText(student_track_syllabus.this, "Student Not Found", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(student_track_syllabus.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle other errors
                    Toast.makeText(student_track_syllabus.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }

        });
        VolleySingleton.getInstance(student_track_syllabus.this).addToRequestQueue(jsonObjectRequest);
    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
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
            SessionManagementStudent sessionManagement = new SessionManagementStudent(student_track_syllabus.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(student_track_syllabus.this, Student_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}