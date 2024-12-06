package com.example.ds2_java;

public class InvalidNameException extends Exception {
    public String InvalidNameException() {
        return "Invalid name. Only letters and apostrophes are allowed, and the name must be at least 3 characters long.";
    }
}
