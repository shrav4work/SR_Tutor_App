package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Enter_topics_Tutor extends AppCompatActivity {

    ArrayList<String> topiclist;
    AutoCompleteTextView enter_topic;
    ListView topics_list;
    ArrayAdapter<String> arrayAdapter1;

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

            }
        });





    }

    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
