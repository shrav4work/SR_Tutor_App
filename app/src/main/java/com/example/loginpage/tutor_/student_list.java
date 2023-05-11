package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.loginpage.R;
import com.example.loginpage.StudentAdapter;
import com.example.loginpage.Student_Details;
import com.example.loginpage.UtilsService.UtilService;
import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;

public class student_list extends AppCompatActivity {
    TextInputLayout studentIdInput;
    AutoCompleteTextView studentIdAuto;
    ArrayList<String> studentIdList;
    ArrayAdapter<String> studentListAdapter;
    UtilService utilService;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

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
        ListView student_list_view = findViewById(R.id.student_list_view);

        Student_Details s1 = new Student_Details("7","Subject1","A DIV","CBSE","20","15","5","30","70");
        Student_Details s2 = new Student_Details("7","Subject1","A DIV","CBSE","20","15","5","30","70");
        Student_Details s3 = new Student_Details("7","Subject1","A DIV","CBSE","20","15","5","30","70");
        ArrayList<Student_Details> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        StudentAdapter adapter = new StudentAdapter(this,R.layout.student_list_adapter,studentList);
        student_list_view.setAdapter(adapter);
        closeKeyboard();

    }
    private void closeKeyboard(){
        View view= this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}