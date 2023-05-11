package com.example.loginpage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student_Details> {
    private Context mContext;
    private static final String TAG = "StudentAdapter";
    int mResource;

    public StudentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Student_Details> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String std = getItem(position).getStd();
        String sub = getItem(position).getSub();
        String div = getItem(position).getDiv();
        Log.i("VAriables", std + "  "+ sub + "  "+ "div");
        String board = getItem(position).getBoard();
        String class_planned = getItem(position).getClass_planned();
        String class_pending = getItem(position).getClass_pending();
        String portion_covered = getItem(position).getPortion_covered();
        String portion_pending = getItem(position).getPortion_pending();
        String class_engaged = getItem(position).getClass_engaged();

        Student_Details s1 = new Student_Details(std,sub,div,board,class_planned,class_engaged,class_pending,portion_covered,portion_pending);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView classText = (TextView) convertView.findViewById(R.id.class_view);
        TextView boardText = (TextView) convertView.findViewById(R.id.board_view);
        TextView subjectText =(TextView) convertView.findViewById(R.id.subject_view);
        TextView classPlannedText = (TextView) convertView.findViewById(R.id.class_planned);
        TextView classEngagedText = (TextView) convertView.findViewById(R.id.class_engaged);
        TextView classPendingText = (TextView) convertView.findViewById(R.id.class_pending);
        TextView portionCoveredText = (TextView) convertView.findViewById(R.id.portion_covered);
        TextView portionpendingText = (TextView) convertView.findViewById(R.id.portion_pending);

        classText.setText("Class: " + std);
        boardText.setText("Board: "+ board);
        subjectText.setText("Subject: "+ sub);
        classPlannedText.setText("Classes Planned: "+ class_planned);
        classEngagedText.setText("Classes Engaged: " + class_engaged);
        classPendingText.setText("Classes Pending: " + class_pending);
        portionCoveredText.setText("Portion Covered: " + portion_covered);
        portionpendingText.setText("Portion Pending: "+ portion_pending);
        return convertView;

    }
}
