package com.example.loginpage.student_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.loginpage.R;
import com.example.loginpage.main_screen.MainActivity;

public class student_home_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_student_home_screen);



        TextView abt_srtutor = findViewById(R.id.student_abt_srtutor);
        abt_srtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(student_home_screen.this);
                View mView = getLayoutInflater().inflate(R.layout.abt_srtut, null);

                alert.setView(mView);
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }
}