package com.example.loginpage.student_.monitor_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.loginpage.R;

import java.util.ArrayList;
import java.util.List;

public class student_monitor_tests extends AppCompatActivity {

    String[] item = {"English","Maths","Kannada","Science","Social Science","Maths"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    RecyclerView recycler_view;
    StudentTestAdapter studentTestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_monitor_tests);

        autoCompleteTextView =findViewById(R.id.stu_select_sub);
        adapter = new ArrayAdapter<String>(this,R.layout.drop_down,item);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }
        });


        recycler_view = findViewById(R.id.recycler_view_student_monitor_tests);
        setRecyclerView();
    }
    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        studentTestAdapter = new StudentTestAdapter(this,getList());
        recycler_view.setAdapter(studentTestAdapter);
    }

    private List<StudentTestModel> getList(){
        List<StudentTestModel> marks_list = new ArrayList<>();
        marks_list.add(new StudentTestModel("Biology","08/07/2019",20,50));
        marks_list.add(new StudentTestModel("Physics","08/07/2019",30,50));
        marks_list.add(new StudentTestModel("Chemistry","08/07/2019",40,50));
        return marks_list;
    }
}