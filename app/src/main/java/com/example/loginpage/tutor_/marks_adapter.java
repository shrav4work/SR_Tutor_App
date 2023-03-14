package com.example.loginpage.tutor_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpage.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

public class marks_adapter extends RecyclerView.Adapter<marks_adapter.ViewHolder> {

    Context context;
    List<Marks_model> marks_list;

    public marks_adapter(Context context,List<Marks_model> marks_list){
        this.context=context;
        this.marks_list=marks_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_rows_viewtests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(marks_list !=null && marks_list.size()>0){
            Marks_model model = marks_list.get(position);
            holder.topic_tv.setText(model.getTopic());
            holder.date_tv.setText(model.getDate());
            holder.marks_scored_tv.setText(model.getMarks()+"");
            holder.max_marks_tv.setText(model.getMax_marks()+"");

        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {

        return marks_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topic_tv,date_tv,marks_scored_tv,max_marks_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topic_tv = itemView.findViewById(R.id.topic_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            marks_scored_tv = itemView.findViewById(R.id.marks_scored_tv);
            max_marks_tv = itemView.findViewById(R.id.max_marks_tv);
        }
    }
}
