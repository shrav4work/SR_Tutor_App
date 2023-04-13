package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.loginpage.R;

public class tutor_about_tests extends AppCompatActivity {

    String[] item = {"English","Maths","Kannada","Science","Social Science","Maths"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    String itemSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_about_tests);


        autoCompleteTextView =findViewById(R.id.autoCompleteTextView_select_subject_addtest);
        adapter = new ArrayAdapter<String>(this,R.layout.drop_down,item);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = parent.getItemAtPosition(position).toString();

            }
        });


        findViewById(R.id.enter_no_of_tests_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(tutor_about_tests.this, tutor_add_number_of_tests.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.enter_tests_abouttest_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(tutor_about_tests.this, tutor_add_test.class);
                intent.putExtra("subjectSelected",itemSelected);
                startActivity(intent);
            }
        });

        findViewById(R.id.view_tests).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(tutor_about_tests.this, tutor_view_tests.class);
                intent.putExtra("subjectSelected",itemSelected);
                startActivity(intent);
            }
        });
    }
}