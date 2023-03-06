package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


public class Set_Syllabus_ extends AppCompatActivity {



    String[] items= {"English","Hindi","Kannada","Science","Social Science","Mathematics"};

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapteritems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_syllabus);

        autoCompleteTxt = findViewById(R.id.autoCompleteTextView1);

        adapteritems = new ArrayAdapter<String>(this,R.layout.drop_down,items);
        autoCompleteTxt.setAdapter(adapteritems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.set_syllabus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Set_Syllabus_.this,Enter_topics_Tutor.class);
                startActivity(intent);
            }
        });

    }
}