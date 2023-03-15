package com.example.loginpage.student_.payment_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.loginpage.R;
import com.example.loginpage.student_.monitor_test.StudentTestAdapter;

public class student_payment_details extends AppCompatActivity {

    RecyclerView recycler_view;
    StudentTestAdapter studentTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_payment_details);
    }
}