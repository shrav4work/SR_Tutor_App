package com.example.loginpage;

public class Student_Details {
    private String std,sub,div,board,class_planned,class_engaged,class_pending,portion_covered,portion_pending;

    public Student_Details(String std, String sub, String div, String board, String class_planned, String class_engaged, String class_pending, String portion_covered, String portion_pending) {
        this.std = std;
        this.sub = sub;
        this.div = div;
        this.board = board;
        this.class_planned = class_planned;
        this.class_engaged = class_engaged;
        this.class_pending = class_pending;
        this.portion_covered = portion_covered;
        this.portion_pending = portion_pending;
    }

    public String getStd() {
        return std;
    }

    public String getSub() {
        return sub;
    }

    public String getDiv() {
        return div;
    }

    public String getBoard() {
        return board;
    }

    public String getClass_planned() {
        return class_planned;
    }

    public String getClass_engaged() {
        return class_engaged;
    }

    public String getClass_pending() {
        return class_pending;
    }

    public String getPortion_covered() {
        return portion_covered;
    }

    public String getPortion_pending() {
        return portion_pending;
    }
}
