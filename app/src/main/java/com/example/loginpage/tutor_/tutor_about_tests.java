package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginpage.R;

public class tutor_about_tests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_about_tests);

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
                startActivity(intent);
            }
        });
    }
}