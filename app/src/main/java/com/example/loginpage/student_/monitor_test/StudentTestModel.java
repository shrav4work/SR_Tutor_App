package com.example.loginpage.student_.monitor_test;

import java.util.Date;

public class StudentTestModel {
    String topic;
    String date;
    int marks;
    int max_marks;

    public StudentTestModel(String topic, String date, int marks, int max_marks){
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

