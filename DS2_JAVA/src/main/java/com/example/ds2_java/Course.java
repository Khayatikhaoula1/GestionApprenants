package com.example.ds2_java;


public class Course {
    private String courseName;

    private int points;

    public Course(String courseName, int points) {
        this.courseName = courseName;

        this.points = points;
    }

    public String getCourseName() {
        return courseName;
    }


    public int getPoints() {
        return points;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

