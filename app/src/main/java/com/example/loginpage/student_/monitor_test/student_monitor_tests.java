package com.example.loginpage.student_.monitor_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.loginpage.R;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Student_Login;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudent;
import com.example.loginpage.tutor_.Marks_model;
import com.example.loginpage.tutor_.marks_adapter;
import com.example.loginpage.tutor_.tutor_view_tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class student_monitor_tests extends AppCompatActivity {

    List<StudentTestModel> marks_list = new ArrayList<>();
    RecyclerView recycler_view;
    StudentTestAdapter marksAdapter;

    String[] item = {"English","Maths","Kannada","Science","Social Science","Maths"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    String itemSelected;
    UtilService utilService;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_monitor_tests);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //dropdown
        autoCompleteTextView =findViewById(R.id.autoCompleteTextView_select_subject_monitor_test);
        adapter = new ArrayAdapter<String>(this,R.layout.drop_down,item);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = parent.getItemAtPosition(position).toString();
                setData();
            }
        });

        //recyclerview to set data
        recycler_view = findViewById(R.id.recycler_view_student_monitor_tests);

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

    }


    public void setData() {
        utilService = new UtilService();
        ip =utilService.getIp();
        SessionManagementStudent sessionManagementStudent = new SessionManagementStudent(student_monitor_tests.this);
        String fetchemail = sessionManagementStudent.getSESSION_KEY();

        final String url = "http://"+ip+":3000/api/test_details_student/"+fetchemail+"/"+itemSelected;

//        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject marksObject = response.getJSONObject(i);
//                        Log.i("response", String.valueOf(response.getJSONObject(i)));
                        StudentTestModel marks_model = new StudentTestModel(
                                marksObject.getString("test_name"),
                                marksObject.getString("date"),
                                marksObject.getInt("marks"),
                                marksObject.getInt("max_marks")
                        );

//                        Log.i("getTopic",marks_model.getTopic());
                        marks_list.add(marks_model);


//                       Log.i("topic", marksObject.getString("topic"));

                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                    marksAdapter = new StudentTestAdapter(student_monitor_tests.this, marks_list);
                    recycler_view.setAdapter(marksAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse:"+ error.getMessage());
            }
        });
        Log.i("Innnnnn","+++++++++++++++++++++++++++++++++");
//        Log.i("Sizeeeeeee",marks_list.size()+" ");
        VolleySingleton.getInstance(student_monitor_tests.this).addToRequestQueue(jsonArrayRequest);
    }

//    private void setRecyclerView() {
//        recycler_view.setHasFixedSize(true);
//        recycler_view.setLayoutManager(new LinearLayoutManager(this));
//        marksAdapter = new marks_adapter(this,marks_list);
//        recycler_view.setAdapter(marksAdapter);
//    }

//    private List<Marks_model> getList(){
//
//        List<Marks_model> marks_list = new ArrayList<>();
//            marks_list.add(new Marks_model("Biology","08/07/2019",20,50));
//            marks_list.add(new Marks_model("Physics","08/07/2019",30,50));
//            marks_list.add(new Marks_model("Chemistry","08/07/2019",40,50));
//        return marks_list;
//    }

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
            SessionManagementStudent sessionManagement = new SessionManagementStudent(student_monitor_tests.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(student_monitor_tests.this, Student_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}