package com.example.ds2_java;

public class InvalidEmailException extends Exception{
    public String InvalidEmailException() {
        return " The email must contain a valid username, @ symbol, domain name. and must not been used";
    }
}
