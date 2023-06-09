package com.example.loginpage.tutor_;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.loginpage.R;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudentInTutor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tutor_view_tests extends AppCompatActivity {


    List<Marks_model> marks_list = new ArrayList<>();
    RecyclerView recycler_view;
    marks_adapter marksAdapter;
    UtilService utilService;
    String subject;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_view_tests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycler_view = findViewById(R.id.recycler_view_viewtests);
        setData();
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));






    }

    public void setData() {
        utilService = new UtilService();
        ip =utilService.getIp();

        SessionManagementStudentInTutor sessionManagementStudentInTutor = new SessionManagementStudentInTutor(tutor_view_tests.this);
        String id = sessionManagementStudentInTutor.getSESSION_KEY();

        subject = getIntent().getStringExtra("subjectSelected");
        final String url = "http://"+ip+":3000/api/test_details/"+id+"/"+subject;

//        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject marksObject = response.getJSONObject(i);
//                        Log.i("response", String.valueOf(response.getJSONObject(i)));
                        Marks_model marks_model = new Marks_model(
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
                    marksAdapter = new marks_adapter(tutor_view_tests.this, marks_list);
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
        VolleySingleton.getInstance(tutor_view_tests.this).addToRequestQueue(jsonArrayRequest);
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
            SessionManagement sessionManagement = new SessionManagement(tutor_view_tests.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(tutor_view_tests.this, Tutor_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}