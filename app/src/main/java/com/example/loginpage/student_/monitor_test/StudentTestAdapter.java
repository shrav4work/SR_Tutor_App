package com.example.loginpage.student_.monitor_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpage.R;

import java.util.List;

public class StudentTestAdapter extends RecyclerView.Adapter<StudentTestAdapter.ViewHolder> {

    Context context;
    List<StudentTestModel> marks_list;

    public StudentTestAdapter(Context context,List<StudentTestModel> marks_list){
        this.context=context;
        this.marks_list=marks_list;
    }

    @NonNull
    @Override
    public StudentTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_recyler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentTestAdapter.ViewHolder holder, int position) {
        if(marks_list !=null && marks_list.size()>0){
            StudentTestModel model1 = marks_list.get(position);
            holder.topic_tv_stu.setText(model1.getTopic());
            holder.date_tv_stu.setText(model1.getDate());
            holder.marks_scored_tv_stu.setText(model1.getMarks()+"");
            holder.max_marks_tv_stu.setText(model1.getMax_marks()+"");

        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {

        return marks_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topic_tv_stu,date_tv_stu,marks_scored_tv_stu,max_marks_tv_stu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topic_tv_stu = itemView.findViewById(R.id.topic_tv_stu);
            date_tv_stu = itemView.findViewById(R.id.date_tv_stu);
            marks_scored_tv_stu = itemView.findViewById(R.id.marks_scored_tv_stu);
            max_marks_tv_stu = itemView.findViewById(R.id.max_marks_tv_stu);
        }
    }
}