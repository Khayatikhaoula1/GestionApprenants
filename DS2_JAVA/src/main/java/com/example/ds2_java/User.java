package com.example.ds2_java;

import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String dateofbirth;
    private String email;
    private final int id;
    private List<Course> Course=new ArrayList<>();

    public  List<Course> getCourse() {
        return Course;
    }

    public void setCourse(Course course) {
        Course.add(course);
    }

    public User(String firstName, String lastName, String birthdate, String email, int id, List<Course> Course) throws InvalidNameException
    {
        if (!isValidName(firstName) || !isValidName(lastName)) {
            throw new InvalidNameException();
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofbirth = birthdate;
        this.email = email;
        this.id = id;
        this.Course = Course ;
    }
    public User(String firstName, String lastName, String birthdate, String email, int id, Course Course) throws InvalidNameException
    {
        if (!isValidName(firstName) || !isValidName(lastName)) {
            throw new InvalidNameException();
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofbirth = birthdate;
        this.email = email;
        this.id = id;
        this.Course.add(Course) ;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) throws InvalidNameException
    { if (!isValidName(firstName) ) {
        throw new InvalidNameException();
    }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws InvalidNameException {
        if (!isValidName(lastName)) {
            throw new InvalidNameException();
        }
        this.lastName = lastName;
    }


    public void setDateofbirth(String birthdate) {
        this.dateofbirth = birthdate;
    }

    public void setEmail(String email) {


        this.email = email;
    }
    public static boolean isValidName(String name) {
        if (name.length() < 3) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String chaine ="";
        for (Course course:Course) {
            chaine+=course.getCourseName()+" "+course.getPoints()+",";

        }
        return firstName + ',' +lastName + ','+ dateofbirth + ','+ email + ',' + id + "," +chaine;
    }
}




