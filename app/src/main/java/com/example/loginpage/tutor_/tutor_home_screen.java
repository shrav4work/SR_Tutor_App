package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.loginpage.R;
import com.example.loginpage.Set_Syllabus_;

public class tutor_home_screen extends AppCompatActivity {

    String student_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_tutor_home_screen);

        student_id=getIntent().getStringExtra("enter_stu_id_geosignin");
        Log.i("Student_id",student_id);


        findViewById(R.id.student_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tutor_home_screen.this,student_list.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.set_syllabus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tutor_home_screen.this,Set_Syllabus_.class);
                intent.putExtra("student_id",student_id);
                startActivity(intent);
            }
        });
        findViewById(R.id.add_tests).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(tutor_home_screen.this, tutor_about_tests.class);
                startActivity(intent);
            }
        });




    }
}