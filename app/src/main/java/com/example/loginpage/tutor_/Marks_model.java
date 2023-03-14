package com.example.loginpage.tutor_;

import java.util.Date;

public class Marks_model {
    String topic;
    String date;
    int marks;
    int max_marks;

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
}
