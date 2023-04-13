package com.example.loginpage.tutor_;

import android.content.Context;

import java.util.Date;
import java.util.List;

public class Marks_model {
    String topic;
    String date;
    int marks;
    int max_marks;


    public Marks_model(){}

    public Marks_model(String topic, String date, int marks, int max_marks){
        this.topic = topic;
        this.date = date;
        this.marks = marks;
        this.max_marks=max_marks;

    }




    public String getTopic() {
        return topic;
    }

    public String getDate() {
        return date;
    }

    public int getMarks() {
        return marks;
    }

    public int getMax_marks() {
        return max_marks;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public void setMax_marks(int max_marks) {
        this.max_marks = max_marks;
    }
}
