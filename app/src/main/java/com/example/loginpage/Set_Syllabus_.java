package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginpage.login_pages.Tutor_Login;
import com.example.loginpage.session_management.SessionManagement;
import com.example.loginpage.session_management.SessionManagementStudentInTutor;
import com.example.loginpage.tutor_.tutor_home_screen;
import com.example.loginpage.tutor_.view_syllabus;


public class Set_Syllabus_ extends AppCompatActivity {

    private int REQUEST_CODE =1;
    String student_id;
    String[] items= {"English","Hindi","Kannada","Science","Social Science","Mathematics"};

    EditText student_sylabus_id;

    String subject;
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapteritems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_syllabus);

        SessionManagementStudentInTutor sessionManagementStudentInTutor = new SessionManagementStudentInTutor(Set_Syllabus_.this);
        String id = sessionManagementStudentInTutor.getSESSION_KEY();
//        student_id=getIntent().getStringExtra("student_id");
        student_sylabus_id = findViewById(R.id.student_sylabus_id);
        student_sylabus_id.setText(id);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        autoCompleteTxt = findViewById(R.id.autoCompleteTextView1);

//        Log.i("Student_id",student_id);



        adapteritems = new ArrayAdapter<String>(this,R.layout.drop_down,items);
        autoCompleteTxt.setAdapter(adapteritems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Subject: "+subject,Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.set_syllabus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subject == null){
                    Toast.makeText(Set_Syllabus_.this, "Please select a subject", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Set_Syllabus_.this,Enter_topics_Tutor.class);
                    intent.putExtra("subject",subject);
                    startActivityForResult(intent,REQUEST_CODE);
                }

            }
        });

        findViewById(R.id.view_set_syllabus_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subject == null){
                    Toast.makeText(Set_Syllabus_.this, "Please select a subject", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Set_Syllabus_.this, view_syllabus.class);
                    intent.putExtra("subject",subject);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1){
            SessionManagement sessionManagement = new SessionManagement(Set_Syllabus_.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(Set_Syllabus_.this, Tutor_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("result")) {
                // Retrieve the result data from the intent
                String resultData = data.getStringExtra("result");

                // Use the resultData as per your requirements
            }
        }
    }
}