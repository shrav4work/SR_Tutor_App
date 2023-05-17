package com.example.loginpage.tutor_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.loginpage.R;
import com.example.loginpage.Set_Syllabus_;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_syllabus extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    ArrayList<DataItem> mList;
    private MyAdapter mAdapter;

    String ip;
    UtilService utilService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_syllabus);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setData();


//        mList.add(new DataItem("This is Task 1"+"   4hrs   ",false));
//        mList.add(new DataItem("This is Task 2"+"   4hrs   ",false));
//        mList.add(new DataItem("This is Task 3"+"   4hrs   ",false));
//        mList.add(new DataItem("This is Task 4"+"   4hrs   ",false));
//        mList.add(new DataItem("This is Task 5"+"   4hrs   ",false));
//        mList.add(new DataItem("This is Task 6"+"   4hrs   ",false));




        mAdapter = new MyAdapter(this , mList, new MyAdapter.OnCheckedChangeListener(){
            public void onItemCheckedChanged(int position, boolean isChecked) {
                mList.get(position).setChecked(isChecked);
                Log.i("isChecked", String.valueOf(isChecked));
                updateData(position, isChecked);
                }
        });
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);




        mAdapter.notifyDataSetChanged();
    }

    private void updateData(int position, boolean isChecked) {
        utilService = new UtilService();
        ip = utilService.getIp();

        final String url = "http://"+ip+":3000/api/update_syllabus/01fe19bcs060/Science";

        Map<String,String> params = new HashMap<>();
        params.put("subject_topic",mList.get(position).getText());
        params.put("completed",String.valueOf(isChecked));

        Log.i("isCheckedInsideUpdateFunction", String.valueOf(isChecked));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String completed = response.getString("completed");
                            Log.i("completed",completed);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if(error instanceof ServerError && response!= null){
                            try{
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                                JSONObject obj = new JSONObject(res);
                                Toast.makeText(view_syllabus.this, obj.getString("completed"),Toast.LENGTH_SHORT).show();
                            }catch(JSONException | UnsupportedEncodingException je){
                                je.printStackTrace();
                            }
                        }
                    }
                });

        VolleySingleton.getInstance(view_syllabus.this).addToRequestQueue(jsonObjectRequest);

    }

    private void setData() {
        utilService = new UtilService();
        ip=utilService.getIp();

        final String url = "http://"+ip+":3000/api/fetch_syllabus/01fe19bcs060/Science";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try{
                        JSONObject syllabusObject = response.getJSONObject(i);
                        mList.add(new DataItem(syllabusObject.getString("subject_topics"),
                                syllabusObject.getBoolean("completed")));
                    }catch (JSONException je){
                        je.printStackTrace();
                    }
                }
                mRecyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse:"+ error.getMessage());
            }
        });


        VolleySingleton.getInstance(view_syllabus.this).addToRequestQueue(jsonArrayRequest);
    }





    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view_syllabus.this);
            builder.setTitle("Delete Task");
            builder.setMessage("Are You Sure ??");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which){
                    int position = viewHolder.getAdapterPosition();
                    mList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            builder.show();
        }

    };


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1){
            SessionManagement sessionManagement = new SessionManagement(view_syllabus.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(view_syllabus.this, Tutor_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}