package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginpage.UtilsService.UtilService;
import com.example.loginpage.UtilsService.VolleySingleton;
import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.tutor_.tutor_home_screen;
import com.example.loginpage.tutor_.tutor_view_tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Enter_topics_Tutor extends AppCompatActivity {

    ArrayList<String> topiclist;
    AutoCompleteTextView enter_topic;
    ListView topics_list;
    ArrayAdapter<String> arrayAdapter1;
    UtilService utilService;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_topics_tutor);

        topics_list=(ListView) findViewById(R.id.topic_list_view);
        enter_topic = (AutoCompleteTextView) findViewById(R.id.enter_topic);

        topiclist = new ArrayList<String>();
        arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,topiclist);



        findViewById(R.id.add_topic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names=enter_topic.getText().toString();
                if(!TextUtils.isEmpty(names)){
                    topiclist.add(names);
                    topics_list.setAdapter(arrayAdapter1);
                    arrayAdapter1.notifyDataSetChanged();
                    closeKeyboard();
                    enter_topic.setText("");
                }else{
                    Toast.makeText(Enter_topics_Tutor.this,"Please enter a topic or click finish to Finish",Toast.LENGTH_LONG).show();
                }


                Log.i("topics list", String.valueOf(topiclist));
            }


        });


        findViewById(R.id.enter_topic_fin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitToBackend(topiclist);
                Intent intent = new Intent(Enter_topics_Tutor.this, tutor_home_screen.class);
                startActivity(intent);
            }
        });





    }

    private void submitToBackend(ArrayList<String> topiclist) {
        utilService = new UtilService();
        ip =utilService.getIp();
        final String url = "http://"+ip+":3000/api/test_api";

        JSONObject requestBody = new JSONObject();

        JSONArray myList = new JSONArray();
        for(int i=0;i<topiclist.size();i++)
            myList.put(topiclist.get(i));

        try {
            requestBody.put("myList", myList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String ok = response.getString("ok");
                    Log.i(ok,ok);
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
                        Toast.makeText(Enter_topics_Tutor.this, obj.getString("msg"),Toast.LENGTH_SHORT).show();
                    }catch(JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }

            }
        });

        VolleySingleton.getInstance(Enter_topics_Tutor.this).addToRequestQueue(request);



    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
