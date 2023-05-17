package com.example.loginpage.tutor_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginpage.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static ArrayList<DataItem> mList  = new ArrayList<>();

    private Context context;
    private static OnCheckedChangeListener listener;

    public interface OnCheckedChangeListener {
        void onItemCheckedChanged(int position, boolean isChecked);
    }

    public MyAdapter(Context context , ArrayList<DataItem> mList,OnCheckedChangeListener listener){
        this.context = context;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataItem item = mList.get(position);
        holder.mCheckBox.setChecked(item.isChecked());
        holder.mCheckBox.setText(item.getText());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        DataItem item = mList.get(position);
                        item.setChecked(isChecked);
                        listener.onItemCheckedChanged(position, isChecked);
                    }
                }
            });
        }
    }
}
