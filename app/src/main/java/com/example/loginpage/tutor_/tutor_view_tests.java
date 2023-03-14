package com.example.loginpage.tutor_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.loginpage.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class tutor_view_tests extends AppCompatActivity {

    RecyclerView recycler_view;
    marks_adapter marksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_view_tests);

        recycler_view = findViewById(R.id.recycler_view_viewtests);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        marksAdapter = new marks_adapter(this,getList());
        recycler_view.setAdapter(marksAdapter);
    }

    private List<Marks_model> getList(){
        List<Marks_model> marks_list = new ArrayList<>();
            marks_list.add(new Marks_model("Biology","08/07/2019",20,50));
            marks_list.add(new Marks_model("Physics","08/07/2019",30,50));
            marks_list.add(new Marks_model("Chemistry","08/07/2019",40,50));
        return marks_list;
    }
}